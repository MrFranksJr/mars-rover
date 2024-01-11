package io.tripled.marsrover.simulation;

public class Simulation {
    private final int simulationSize;

    public Simulation(int simulationSize) {
        if(simulationSize < 0) throw new RuntimeException("The value " + simulationSize + " should be positive");
        this.simulationSize = simulationSize;
    }

    public int getNrOfCoordinates() {
        return (int) Math.pow(simulationSize + 1, 2);
    }

    public int getSimulationSize() {
        return simulationSize;
    }
}