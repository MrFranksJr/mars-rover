package io.tripled.marsrover.simulation;

public interface SimulationRepository {
    void add(Simulation simulation);

    Simulation getSimulation();
}
