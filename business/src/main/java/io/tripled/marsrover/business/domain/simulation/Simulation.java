package io.tripled.marsrover.business.domain.simulation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.rover.*;
import io.tripled.marsrover.vocabulary.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Simulation {
    private final int simulationSize;
    private final Multimap<Location, Rover> roverLocationMap;
    private final SimulationId id;
    private int nrOfRovers = 0;

    public Simulation(int simulationSize) {
        if (simulationSize < 0) throw new RuntimeException("The value " + simulationSize + " should be positive");
        this.id = SimulationId.create();
        this.simulationSize = simulationSize;
        this.roverLocationMap = MultimapBuilder.hashKeys().arrayListValues().build();
    }

    private Simulation(Builder builder) {
        id = builder.id;
        simulationSize = builder.simulationSize;
        roverLocationMap = builder.roverLocationMap;
        nrOfRovers = builder.nrOfRovers;
    }

    private Simulation(SimulationSnapshot simulationSnapshot) {
        this.roverLocationMap = MultimapBuilder.hashKeys().arrayListValues().build();
        this.id = simulationSnapshot.id();
        simulationSize = simulationSnapshot.simulationSize();
        for (RoverState roverState : simulationSnapshot.roverList()) {
            Rover r = new Rover(roverState.roverId(), roverState.roverHeading(), roverState.hitpoints(), roverState.healthState());
            final var lo = Location.newBuilder().setSimulationSize(simulationSize).withCoordinate(roverState.coordinate()).build();
            roverLocationMap.put(lo, r);
        }
        nrOfRovers = roverLocationMap.size();
    }

    public static Optional<Simulation> create(int size) {
        return size < 0 ? Optional.empty() : Optional.of(new Simulation(size));
    }

    private static boolean checkIfRoverIsBroken(Rover rover, Location oldLocation) {
        return rover.getRoverState(oldLocation).healthState() == HealthState.BROKEN;
    }

    private static boolean roverIsAlive(Location newLocation, Rover rover) {
        return rover.getRoverState(newLocation).healthState() == HealthState.OPERATIONAL;
    }

    public static Simulation of(SimulationSnapshot simulationSnapshot) {
        return new Simulation(simulationSnapshot);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public SimulationSnapshot takeSnapshot() {
        final var collect = roverLocationMap.entries().stream().map((Map.Entry<Location, Rover> x) -> x.getValue().getRoverState(x.getKey())).toList();
        return SimulationSnapshot.newBuilder()
                .withId(id)
                .withSimSize(simulationSize)
                .withTotalCoordinates(calculateNrOfCoordinates())
                .withRoverList(collect)
                .build();
    }

    public void landRover(Coordinate coordinate, SimulationLandingEventPublisher eventPublisher) {
        if (invalidCoordinatesReceived(coordinate)) {
            eventPublisher.publish(new InvalidCoordinatesReceived(coordinate));
        } else if (!landsOnTopOfOtherRover(coordinate).isEmpty()) {
            final Location landingLocation = new Location(coordinate, simulationSize);
            final RoverState landingRoverState = landRover(coordinate);
            final List<RoverId> hitRoverIds = landsOnTopOfOtherRover(coordinate);

            roverLocationMap.get(landingLocation)
                    .forEach(Rover::breakRover);

            eventPublisher.publish(new LandingOnTopEvent(landingRoverState, hitRoverIds, coordinate));
        } else if (landingWithinSimulationLimits(coordinate)) {
            final RoverState roverState = landRover(coordinate);
            eventPublisher.publish(new LandingSuccessfulLandEvent(roverState));
        } else {
            eventPublisher.publish(new RoverMissesSimulationLand(simulationSize, coordinate));
        }
    }

    //Todo: cleanup
    public void moveRovers(ImmutableList<RoverInstructions> batch) {
        // List<ExtrapolatedInstruction> extrapolatedInstructions = buildSingleStepInstructions(batch);
        // extrapolated list of instruction R1
        // extrapolated list of instruction R2
        // determine longest instructionList = length
        // loop over length
        // execute move on index of length R1
        // execute move on index of length R2
    }

    //Todo: cleanup
    public List<List<Pair<RoverId, RoverMove>>> buildSingleStepInstructions(ImmutableList<RoverInstructions> batch) {
        List<List<Pair<RoverId, RoverMove>>> extrapolatedInstructions = new ArrayList<>();

        for (RoverInstructions roverInstructions : batch) {
            RoverId roverId = roverInstructions.id();
            List<Pair<RoverId, RoverMove>> singleStepRoverMoves = new ArrayList<>();
            for (RoverMove roverMove : roverInstructions.moves()) {
                Direction direction = roverMove.direction();
                for (int i = 0; i < roverMove.steps(); i++) {
                    singleStepRoverMoves.add(new Pair<>(roverId, new RoverMove(direction, 1)));
                }
            }
            extrapolatedInstructions.add(singleStepRoverMoves);
        }
        return extrapolatedInstructions;
    }

    private List<RoverId> landsOnTopOfOtherRover(Coordinate coordinate) {
        List<RoverId> hitRoverIds = new ArrayList<>();
        SimulationSnapshot simulationSnapshot = this.takeSnapshot();
        for (RoverState roverState : simulationSnapshot.roverList()) {
            if (roverState.coordinate().equals(coordinate)) {
                hitRoverIds.add(roverState.roverId());
            }
        }
        return hitRoverIds;
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
                        case RoverMovedSuccessfulEvent roverMovedSuccessfulEvent ->
                                eventPublisher.publish(roverMovedSuccessfulEvent);
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

    private SimulationMoveRoverEvent moveRover(Direction direction, Pair<Location, Rover> locationRoverPair) {
        final Location oldLocation = locationRoverPair.first();
        final Rover rover = locationRoverPair.second();
        if (checkIfRoverIsBroken(rover, oldLocation)) {
            return new RoverAlreadyBroken(rover.getRoverId());
        }

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
        return new Rover(roverId, RoverHeading.NORTH, 5, HealthState.OPERATIONAL);
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

    public record LandingOnTopEvent(RoverState landingRoverState, List<RoverId> hitRoverIds,
                                    Coordinate coordinate) implements SimulationLandEvent {
    }

    /**
     * The public Events of the Simulation aggregate
     */
    public record LandingSuccessfulLandEvent(RoverState roverState) implements SimulationLandEvent {
    }

    public record RoverMissesSimulationLand(int simulationSize, Coordinate coordinate) implements SimulationLandEvent {
    }

    public record InvalidCoordinatesReceived(Coordinate coordinate) implements SimulationLandEvent {
    }

    public record RoverMovedSuccessfulEvent(RoverState roverState) implements SimulationMoveRoverEvent {
    }

    public record RoverCollided(RoverState roverState, Location newLocation) implements SimulationMoveRoverEvent {
    }

    public record RoverDeath(RoverState roverState) implements SimulationMoveRoverEvent {
    }

    public record RoverAlreadyBroken(RoverId roverId) implements SimulationMoveRoverEvent {
    }

    public static final class Builder {
        private final Multimap<Location, Rover> roverLocationMap;
        public SimulationId id;
        private int simulationSize;
        private int nrOfRovers;

        private Builder() {
            roverLocationMap = MultimapBuilder.hashKeys().arrayListValues().build();
        }

        public Builder withSimulationSize(int val) {
            simulationSize = val;
            return this;
        }

        public Builder withId(SimulationId val) {
            id = val;
            return this;
        }

        public Builder withRoverLocations(List<RoverState> val) {
            for (RoverState roverState : val) {
                Rover r = new Rover(roverState.roverId(), roverState.roverHeading(), roverState.hitpoints(), roverState.healthState());
                final var lo = Location.newBuilder().setSimulationSize(simulationSize).withCoordinate(roverState.coordinate()).build();
                roverLocationMap.put(lo, r);
            }

            nrOfRovers = val.size();
            return this;
        }

        public Simulation build() {
            return new Simulation(this);
        }
    }
}