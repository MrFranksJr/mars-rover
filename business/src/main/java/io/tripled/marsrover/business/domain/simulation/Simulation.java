package io.tripled.marsrover.business.domain.simulation;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.domain.rover.*;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.RoverInstructions;
import io.tripled.marsrover.vocabulary.RoverMove;

import java.util.Map;
import java.util.Optional;

public class Simulation {
    private final int simulationSize;
    private final Multimap<Location, Rover> roverLocationMap;
    private int nrOfRovers = 0;

    public Simulation(int simulationSize) {
        if (simulationSize < 0) throw new RuntimeException("The value " + simulationSize + " should be positive");
        this.simulationSize = simulationSize;
        this.roverLocationMap = MultimapBuilder.hashKeys().arrayListValues().build();
    }

    public static Optional<Simulation> create(int size) {
        return size < 0 ? Optional.empty() : Optional.of(new Simulation(size));
    }

    public SimulationState takeSnapshot() {
        final var collect = roverLocationMap.entries().stream().map((Map.Entry<Location, Rover> x) -> x.getValue().getRoverState(x.getKey())).toList();
        return SimulationState.newBuilder()
                .withSimSize(simulationSize)
                .withTotalCoordinates(calculateNrOfCoordinates())
                .withRoverList(collect)
                .build();
    }

    public void landRover(Coordinate coordinate, SimulationLandingEventPublisher eventPublisher) {
        if (invalidCoordinatesReceived(coordinate)) {
            eventPublisher.publish(new InvalidCoordinatesReceived(coordinate));
        } else if (landingWithinSimulationLimits(coordinate)) {
            final RoverState roverState = landRover(coordinate);
            eventPublisher.publish(new LandingSuccessfulLandEvent(roverState));
        } else {
            eventPublisher.publish(new RoverMissesSimulationLand(simulationSize));
        }
    }


    public void moveRover(RoverInstructions roverInstructions, SimulationRoverMovedEventPublisher eventPublisher) {
        for (RoverMove roverMove : roverInstructions.moves()) {
            for (int i = 0; i < roverMove.steps(); i++) {

                final Optional<SimulationMoveRoverEvent> roverEvent = getRover(roverInstructions.id())
                        .map(x -> moveRover(roverMove.direction(), x));

                //TODO improve
                if (roverEvent.isPresent()) {
                    final SimulationMoveRoverEvent e = roverEvent.get();
                    switch (e) {
                        case RoverCollided roverCollided -> {
                            eventPublisher.publish(roverCollided);
                            return;
                        }
                        case RoverMovedSuccessfulEvent roverMovedSuccessfulEvent -> eventPublisher.publish(roverMovedSuccessfulEvent);
                        case RoverDeath roverDeath -> {
                            eventPublisher.publish(roverDeath);
                            return;
                        }
                        case RoverAlreadyBroken roverAlreadyBroken -> eventPublisher.publish(roverAlreadyBroken);
                    }
                }
            }
        }
    }



    /**
     * The public Events of the Simulation aggregate
     */
    public record LandingSuccessfulLandEvent(RoverState roverState) implements SimulationLandEvent {}

    public record RoverMissesSimulationLand(int simulationSize) implements SimulationLandEvent {}

    public record InvalidCoordinatesReceived(Coordinate coordinate) implements SimulationLandEvent {}

    public record RoverMovedSuccessfulEvent(RoverState roverState) implements SimulationMoveRoverEvent {}

    public record RoverCollided(RoverState roverState, Location newLocation) implements SimulationMoveRoverEvent {}

    public record RoverDeath(RoverState roverState) implements SimulationMoveRoverEvent {}

    private SimulationMoveRoverEvent moveRover(Direction direction, Pair<Location, Rover> locationRoverPair) {
        final Location oldLocation = locationRoverPair.first();
        final Rover rover = locationRoverPair.second();
        if(checkIfRoverIsBroken(rover, oldLocation)) { return new RoverAlreadyBroken(rover.getRoverId()); }

        return switch (direction) {
            case FORWARD -> {
                final Location newLocation = rover.moveForward(oldLocation);
                yield roverGoToNewLocation(newLocation, oldLocation, rover);
            }
            case BACKWARD -> {
                final Location newLocation = rover.moveBackward(oldLocation);
                yield roverGoToNewLocation(newLocation, oldLocation, rover);
            }
            case LEFT -> {
                rover.turnLeft();
                yield new RoverMovedSuccessfulEvent(rover.getRoverState(oldLocation));
            }
            case RIGHT -> {
                rover.turnRight();
                yield new RoverMovedSuccessfulEvent(rover.getRoverState(oldLocation));
            }
        };
    }

    private static boolean checkIfRoverIsBroken(Rover rover, Location oldLocation) {
        return rover.getRoverState(oldLocation).healthState() == OperationalStatus.BROKEN;
    }

    private SimulationMoveRoverEvent roverGoToNewLocation(Location newLocation, Location oldLocation, Rover rover) {
        if (isFree(newLocation)) {
            changeRoverLocation(oldLocation, rover, newLocation);
            return new RoverMovedSuccessfulEvent(rover.getRoverState(newLocation));
        } else {

            rover.handleDamage();
            if (roverIsAlive(newLocation, rover)) {
                return new RoverCollided(rover.getRoverState(newLocation), newLocation);
            } else {
                return new RoverDeath(rover.getRoverState(newLocation));
            }
        }
    }

    private static boolean roverIsAlive(Location newLocation, Rover rover) {
        return rover.getRoverState(newLocation).healthState() == OperationalStatus.OPERATIONAL;
    }

    private boolean isFree(Location newLocation) {
        return roverLocationMap.get(newLocation).isEmpty();
    }

    private void changeRoverLocation(Location oldLocation, Rover rover, Location newLocation) {
        roverLocationMap.remove(oldLocation, rover);
        roverLocationMap.put(newLocation, rover);
    }

    private Optional<Pair<Location, Rover>> getRover(RoverId roverId) {
        return roverLocationMap.entries().stream()
                .filter(x -> x.getValue().getRoverId().equals(roverId))
                .map(x -> new Pair<>(x.getKey(), x.getValue()))
                .findFirst();
    }

    private int calculateNrOfCoordinates() {
        return (int) Math.pow(simulationSize + 1, 2);
    }

    private RoverState landRover(Coordinate coordinate) {
        Rover r = createNewRover();
        final var lo = Location.newBuilder().setSimulationSize(simulationSize).withCoordinate(coordinate).build();
        roverLocationMap.put(lo, r);
        return r.getRoverState(lo);
    }

    private Rover createNewRover() {
        final var roverId = new RoverId("R" + (++nrOfRovers));
        return new Rover(roverId, RoverHeading.NORTH);
    }

    private boolean invalidCoordinatesReceived(Coordinate coordinate) {
        return coordinate.xCoordinate() < 0 || coordinate.yCoordinate() < 0;
    }

    private boolean landingWithinSimulationLimits(Coordinate coordinate) {
        return coordinate.xCoordinate() <= simulationSize && coordinate.yCoordinate() <= simulationSize;
    }

    public sealed interface SimulationLandEvent {
    }

    public interface SimulationLandingEventPublisher {
        void publish(SimulationLandEvent event);
    }

    public sealed interface SimulationMoveRoverEvent {
    }

    public interface SimulationRoverMovedEventPublisher {
        void publish(SimulationMoveRoverEvent event);
    }

    public record RoverAlreadyBroken(RoverId roverId) implements SimulationMoveRoverEvent {
    }
}