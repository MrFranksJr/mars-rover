package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.domain.rover.Rover;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final int simulationSize;
    private final List<Rover> roverList;

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
        if (roverList.isEmpty()) {
            roverList.add(r1);
        }
    }
}