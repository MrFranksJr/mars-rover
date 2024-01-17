package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
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

    public void landRover(int xCoordinate, int yCoordinate, SimulationEventPublisher eventPublisher) {
        if (isRoverPresent()) {
            eventPublisher.publish(new SimulationAlreadyPopulated(getRoverList().getFirst().getState()));
        } else if (invalidCoordinatesReceived(xCoordinate, yCoordinate)) {
            eventPublisher.publish(new InvalidCoordinatesReceived(xCoordinate, yCoordinate));
        } else if (landingWithinSimulationLimits(xCoordinate, yCoordinate)) {
            eventPublisher.publish(new LandingSuccessfulEvent(landRover(xCoordinate, yCoordinate)));
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

    private RoverState landRover(int xCoordinate, int yCoordinate) {
        Rover r1 = new Rover("R1", xCoordinate, yCoordinate);
        roverList.add(r1);
        return r1.getState();
    }


    private boolean invalidCoordinatesReceived(int xCoordinate, int yCoordinate) {
        return xCoordinate < 0 || yCoordinate < 0;
    }

    private boolean landingWithinSimulationLimits(int xCoordinate, int yCoordinate) {
        return xCoordinate <= simulationSize && yCoordinate <= simulationSize;
    }


    public sealed interface SimulationEvent { }

    public interface SimulationEventPublisher {
        void publish(SimulationEvent event);
    }

    public record LandingSuccessfulEvent(RoverState roverState) implements SimulationEvent {
    }

    public record SimulationAlreadyPopulated(RoverState roverState) implements SimulationEvent {
    }

    public record RoverMissesSimulation(int simulationSize) implements SimulationEvent {
    }

    public record InvalidCoordinatesReceived(int xCoordinate, int yCoordinate) implements SimulationEvent {
    }
}