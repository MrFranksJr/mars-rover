package io.tripled.marsrover.business.domain.simulation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.tripled.marsrover.api.rover.*;
import io.tripled.marsrover.api.simulation.SimulationLandEventPublisher;
import io.tripled.marsrover.api.simulation.SimulationMoveRoverEvent;
import io.tripled.marsrover.api.simulation.SimulationRoverMovedEventPublisher;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.business.domain.rover.Rover;
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
    private final String simulationName;

    public Simulation(int simulationSize) {
        if (simulationSize < 0) throw new RuntimeException("The value " + simulationSize + " should be positive");
        this.id = SimulationId.create();
        this.simulationName = "NewSimulation";
        this.simulationSize = simulationSize;
        this.roverLocationMap = MultimapBuilder.hashKeys().arrayListValues().build();
    }

    private Simulation(Builder builder) {
        id = builder.id;
        simulationName = builder.simulationName;
        simulationSize = builder.simulationSize;
        roverLocationMap = builder.roverLocationMap;
        nrOfRovers = builder.nrOfRovers;
    }

    public Simulation(String simulationName, int size) {
        if (size < 0) throw new RuntimeException("The value " + size + " should be positive");
        this.id = SimulationId.create();
        this.simulationName = simulationName;
        this.simulationSize = size;
        this.roverLocationMap = MultimapBuilder.hashKeys().arrayListValues().build();
    }

    private Simulation(SimulationSnapshot simulationSnapshot) {
        this.roverLocationMap = MultimapBuilder.hashKeys().arrayListValues().build();
        this.id = simulationSnapshot.id();
        this.simulationName = simulationSnapshot.simulationName();
        simulationSize = simulationSnapshot.simulationSize();
        for (RoverState roverState : simulationSnapshot.roverList()) {
            Rover r = new Rover(roverState.roverId(), roverState.heading(), roverState.hitpoints(), roverState.healthState());
            final var lo = Location.newBuilder().setSimulationSize(simulationSize).withCoordinate(roverState.coordinate()).build();
            roverLocationMap.put(lo, r);
        }
        nrOfRovers = roverLocationMap.size();
    }


    public static Optional<Simulation> create(int size) {
        return size < 0 ? Optional.empty() : Optional.of(new Simulation(size));
    }

    public static Optional<Simulation> create(String simulationName, int size) {
        return size < 0 ? Optional.empty() : Optional.of(new Simulation(simulationName, size));
    }

    public static Optional<Simulation> create(String simulationName) {
        return Optional.of(new Simulation(simulationName, 10));
    }

    private static boolean checkIfRoverIsBroken(Rover rover, Location oldLocation) {
        return rover.getRoverState(oldLocation).healthState() == HealthState.BROKEN;
    }

    private static boolean roverIsOperational(Location newLocation, Rover rover) {
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
                .withName(simulationName)
                .withSimSize(simulationSize)
                .withTotalCoordinates(calculateNrOfCoordinates())
                .withRoverList(collect)
                .build();
    }

    public void landRover(Coordinate coordinate, SimulationLandEventPublisher eventPublisher) {
        if (invalidCoordinatesReceived(coordinate)) {
            eventPublisher.publish(new InvalidCoordinatesReceived(id, coordinate));
        } else if (!landsOnTopOfOtherRover(coordinate).isEmpty()) {
            final Location landingLocation = new Location(coordinate, simulationSize);
            final RoverState landingRoverState = landRover(coordinate);

            roverLocationMap.get(landingLocation)
                    .forEach(Rover::breakRover);

            eventPublisher.publish(new LandingOnTopEvent(id, landingRoverState, coordinate));
        } else if (landingWithinSimulationLimits(coordinate)) {
            final RoverState roverState = landRover(coordinate);
            eventPublisher.publish(new LandingSuccessfulLandEvent(id, roverState));
        } else {
            eventPublisher.publish(new RoverMissesSimulationLandEvent(id, simulationSize, coordinate));
        }
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
                final Optional<SimulationMoveRoverEvent> roverMoveEvent = getRover(roverInstructions.id()).map(x -> moveRover(roverMove.direction(), x));

                //TODO improve
                final SimulationMoveRoverEvent e = roverMoveEvent.orElseThrow();
                switch (e) {
                    case RoverMovedSuccessfulEvent roverMovedSuccessfulEvent ->
                            eventPublisher.publish(roverMovedSuccessfulEvent);
                    case RoverAlreadyBrokenEvent roverAlreadyBroken -> eventPublisher.publish(roverAlreadyBroken);
                    case RoverCollidedEvent roverCollided -> {
                        eventPublisher.publish(roverCollided);
                        return;
                    }
                    case RoverBreaksDownEvent roverDeath -> {
                        eventPublisher.publish(roverDeath);
                        return;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + e);
                }
            }
        }
    }

    private SimulationMoveRoverEvent moveRover(Direction direction, Pair<Location, Rover> locationRoverPair) {
        final Location oldLocation = locationRoverPair.first();
        final Rover rover = locationRoverPair.second();
        if (checkIfRoverIsBroken(rover, oldLocation)) {
            return new RoverAlreadyBrokenEvent(id, rover.getRoverId());
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
                yield new RoverMovedSuccessfulEvent(id, rover.getRoverState(oldLocation));
            }
            case RIGHT -> {
                rover.turnRight();
                yield new RoverMovedSuccessfulEvent(id, rover.getRoverState(oldLocation));
            }
        };
    }

    private SimulationMoveRoverEvent roverGoToNewLocation(Location newLocation, Location oldLocation, Rover rover) {
        if (isFree(newLocation)) {
            changeRoverLocation(oldLocation, rover, newLocation);
            return new RoverMovedSuccessfulEvent(id, rover.getRoverState(newLocation));
        } else {
            rover.handleDamage();
            if (roverIsOperational(newLocation, rover)) {
                return new RoverCollidedEvent(id, rover.getRoverState(newLocation), newLocation);
            } else {
                return new RoverBreaksDownEvent(id, rover.getRoverState(newLocation));
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
        return new Rover(roverId, Heading.NORTH, 5, HealthState.OPERATIONAL);
    }

    private boolean invalidCoordinatesReceived(Coordinate coordinate) {
        return coordinate.xCoordinate() < 0 || coordinate.yCoordinate() < 0;
    }

    private boolean landingWithinSimulationLimits(Coordinate coordinate) {
        return coordinate.xCoordinate() <= simulationSize && coordinate.yCoordinate() <= simulationSize;
    }

    /**
     * The public Events of the Simulation aggregate
     */

    public static final class Builder {
        private final Multimap<Location, Rover> roverLocationMap;
        public SimulationId id;
        public String simulationName;
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
                Rover r = new Rover(roverState.roverId(), roverState.heading(), roverState.hitpoints(), roverState.healthState());
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