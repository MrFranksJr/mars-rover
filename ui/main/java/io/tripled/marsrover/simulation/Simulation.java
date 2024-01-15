package io.tripled.marsrover.simulation;

import io.tripled.marsrover.rover.Rover;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final int simulationSize;
    private List<Rover> roverList;

    public Simulation(int simulationSize) {
        if(simulationSize < 0) throw new RuntimeException("The value " + simulationSize + " should be positive");
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

    public void addRover(Rover r1) {
        roverList.add(r1);
    }
}