package io.tripled.marsroverbusiness.simulation;

public interface SimulationRepository {
    void add(Simulation simulation);

    Simulation getSimulation();
}
