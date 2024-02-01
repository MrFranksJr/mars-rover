package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.business.domain.rover.RoverMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public int getNrOfCoordinates() {
        return (int) Math.pow(simulationSize + 1, 2);
    }

    public int getSimulationSize() {
        return simulationSize;
    }
    public List<Rover> getRoverList() {
        return roverList;
    }

    public SimulationState getState() {
        return SimulationState.newBuilder()
                .withSimSize(simulationSize)
                .withTotalCoordinates(getNrOfCoordinates())
                .withRoverList(roverList)
                .build();
    }

    public void landRover(Coordinate coordinate, SimulationLandingEventPublisher eventPublisher) {
        if (isRoverPresent()) {
            eventPublisher.publish(new SimulationLandAlreadyPopulated(getRoverList().getFirst().getState()));
        } else if (invalidCoordinatesReceived(coordinate)) {
            eventPublisher.publish(new InvalidCoordinatesReceived(coordinate));
        } else if (landingWithinSimulationLimits(coordinate)) {
            eventPublisher.publish(new LandingSuccessfulLandEvent(landRover(coordinate)));
        } else {
            eventPublisher.publish(new RoverMissesSimulationLand(simulationSize));
        }
    }

    public void moveRover(List<RoverMove> roverMoves, SimulationRoverMovedEventPublisher eventPublisher) {
        eventPublisher.publish(new RoverMovedSuccessfulEvent(moveRover(roverMoves)));
    }


    public SimulationState simulationState() {
        return new SimulationState(simulationSize, getNrOfCoordinates(), getRoverList());
    }

    public void moveRover(Direction direction) {
        if (direction == Direction.FORWARD) {
            roverList.getFirst().moveForward();
        } else if (direction == Direction.BACKWARD) {
            roverList.getFirst().moveBackward();
        } else if (direction == Direction.LEFT)
            roverList.getFirst().turnLeft();
        else
            roverList.getFirst().turnRight();
    }

    public void turnRover(Direction direction) {
        moveRover(direction);
    }

    public record LandingSuccessfulLandEvent(RoverState roverState) implements SimulationLandEvent {
    }

    public record SimulationLandAlreadyPopulated(RoverState roverState) implements SimulationLandEvent {
    }

    public record RoverMissesSimulationLand(int simulationSize) implements SimulationLandEvent {
    }

    public record InvalidCoordinatesReceived(Coordinate coordinate) implements SimulationLandEvent {
    }

    public record RoverMovedSuccessfulEvent(RoverState roverState) implements SimulationMoveRoverEvent {

    }

    private boolean isRoverPresent() {
        return !roverList.isEmpty();
    }

    private RoverState landRover(Coordinate coordinate) {
        Rover r1 = new Rover("R1", coordinate.xCoordinate(), coordinate.yCoordinate(), simulationSize);
        roverList.add(r1);
        return r1.getState();
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
                moveRover(Direction.convertTextToDirection(roverMove.direction()));
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