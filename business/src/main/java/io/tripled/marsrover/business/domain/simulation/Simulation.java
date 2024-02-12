package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.vocabulary.RoverId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Simulation {
    private final int simulationSize;
    private final List<Rover> roverList;

    public Simulation(int simulationSize) {
        if (simulationSize < 0) throw new RuntimeException("The value " + simulationSize + " should be positive");
        this.simulationSize = simulationSize;
        this.roverList = new ArrayList<>();
    }

    public static Optional<Simulation> create(int size) {
        return size < 0 ? Optional.empty() : Optional.of(new Simulation(size));
    }

    public SimulationState takeSnapshot() {
        final var roverState = roverList.stream()
                .map(Rover::getState)
                .collect(toList());
        return SimulationState.newBuilder()
                .withSimSize(simulationSize)
                .withTotalCoordinates(calculateNrOfCoordinates())
                .withRoverList(roverState)
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

    public void moveRover(List<RoverMove> roverMoves, SimulationRoverMovedEventPublisher eventPublisher) {
        final RoverState roverState = moveRover(roverMoves);
        eventPublisher.publish(new RoverMovedSuccessfulEvent(roverState));
    }

    public void moveRover(RoverId roverId, Direction direction) {
        getRover(roverId.id()).ifPresent(rover -> moveRover(direction, rover));
    }

    /**
     * The public Events of the Simulation aggregate
     */
    public record LandingSuccessfulLandEvent(RoverState roverState) implements SimulationLandEvent {
    }

    public record RoverMissesSimulationLand(int simulationSize) implements SimulationLandEvent {
    }

    public record InvalidCoordinatesReceived(Coordinate coordinate) implements SimulationLandEvent {
    }

    public record RoverMovedSuccessfulEvent(RoverState roverState) implements SimulationMoveRoverEvent {

    }

    private void moveRover(Direction direction, Rover rover) {
        if (direction == Direction.FORWARD) {
            rover.moveForward();
        } else if (direction == Direction.BACKWARD) {
            rover.moveBackward();
        } else if (direction == Direction.LEFT)
            rover.turnLeft();
        else
            rover.turnRight();
    }

    private Optional<Rover> getRover(String roverId) {
        return roverList.stream().filter(x -> x.getRoverId().equals(roverId)).findFirst();
    }

    private int calculateNrOfCoordinates() {
        return (int) Math.pow(simulationSize + 1, 2);
    }

    private RoverState landRover(Coordinate coordinate) {

        Rover r = createNewRover(coordinate);
        roverList.add(r);
        return r.getState();
    }

    private Rover createNewRover(Coordinate coordinate) {
        final var roverId = "R" + (roverList.size() + 1);
        return new Rover(roverId, coordinate.xCoordinate(), coordinate.yCoordinate(), simulationSize);
    }

    private boolean invalidCoordinatesReceived(Coordinate coordinate) {
        return coordinate.xCoordinate() < 0 || coordinate.yCoordinate() < 0;
    }

    private boolean landingWithinSimulationLimits(Coordinate coordinate) {
        return coordinate.xCoordinate() <= simulationSize && coordinate.yCoordinate() <= simulationSize;
    }

    private RoverState moveRover(List<RoverMove> roverMoves) {
        for (RoverMove roverMove : roverMoves) {
            for (int i = 0; i < roverMove.steps(); i++) {
                moveRover(roverMove.roverId(), Direction.convertTextToDirection(roverMove.direction()));
            }
        }
        return roverList.getFirst().getState();
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
}