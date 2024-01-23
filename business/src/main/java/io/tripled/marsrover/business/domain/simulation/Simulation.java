package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.Rover;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final int simulationSize;
    private final List<Rover> roverList;

    public Simulation(int simulationSize) {
        if (simulationSize < 0) throw new RuntimeException("The value " + simulationSize + " should be positive");
        this.simulationSize = simulationSize;
        this.roverList = new ArrayList<>();
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
//    public void landRover(int x, int y, SimulationEventPublisher eventPublisher) {
//        landRover(new Coordinate(x,y),eventPublisher);
//    }

    public void landRover(Coordinate coordinate, SimulationEventPublisher eventPublisher) {
        if (isRoverPresent()) {
            eventPublisher.publish(new SimulationAlreadyPopulated(getRoverList().getFirst().getState()));
        } else if (invalidCoordinatesReceived(coordinate)) {
            eventPublisher.publish(new InvalidCoordinatesReceived(coordinate));
        } else if (landingWithinSimulationLimits(coordinate)) {
            eventPublisher.publish(new LandingSuccessfulEvent(landRover(coordinate)));
        } else {
            eventPublisher.publish(new RoverMissesSimulation(simulationSize));
        }
    }

    public SimulationState simulationState() {
        return new SimulationState(simulationSize, getSimulationSize(), getRoverList());
    }

    private boolean isRoverPresent() {
        return !roverList.isEmpty();
    }

//    private RoverState landRover(int xCoordinate, int yCoordinate) {
//        Rover r1 = new Rover("R1", xCoordinate, yCoordinate);
//        roverList.add(r1);
//        return r1.getState();
//    }

    private RoverState landRover(Coordinate coordinate) {
        Rover r1 = new Rover("R1", coordinate.xCoordinate(), coordinate.yCoordinate());
        roverList.add(r1);
        return r1.getState();
    }


    private boolean invalidCoordinatesReceived(Coordinate coordinate) {
        return coordinate.xCoordinate() < 0 || coordinate.yCoordinate() < 0;
    }

    private boolean landingWithinSimulationLimits(Coordinate coordinate) {
        return coordinate.xCoordinate() <= simulationSize && coordinate.yCoordinate() <= simulationSize;
    }

    public void moveRover(Direction direction) {
        if (direction == Direction.FORWARD) {
            roverList.getFirst().moveForward();
        } else if (direction == Direction.BACKWARD) {
            roverList.getFirst().moveBackward();
        }
    }

    public void turnRover(Direction direction) {
        if (direction == Direction.LEFT)
            roverList.getFirst().turnLeft();
        else
            roverList.getFirst().turnRight();
    }

    public sealed interface SimulationEvent {
    }

    public interface SimulationEventPublisher {
        void publish(SimulationEvent event);
    }

    public record LandingSuccessfulEvent(RoverState roverState) implements SimulationEvent {
    }

    public record SimulationAlreadyPopulated(RoverState roverState) implements SimulationEvent {
    }

    public record RoverMissesSimulation(int simulationSize) implements SimulationEvent {
    }

    public record InvalidCoordinatesReceived(Coordinate coordinate) implements SimulationEvent {
    }
}