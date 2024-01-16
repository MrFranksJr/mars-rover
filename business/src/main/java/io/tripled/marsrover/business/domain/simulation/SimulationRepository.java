package io.tripled.marsrover.business.domain.simulation;

public interface SimulationRepository {
    void add(Simulation simulation);

    Simulation getSimulation();
}
