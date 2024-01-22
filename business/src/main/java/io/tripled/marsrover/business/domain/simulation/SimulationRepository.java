package io.tripled.marsrover.business.domain.simulation;

import org.springframework.stereotype.Repository;

public interface SimulationRepository {
    void add(Simulation simulation);

    Simulation getSimulation();
}
