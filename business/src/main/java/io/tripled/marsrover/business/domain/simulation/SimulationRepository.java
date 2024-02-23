package io.tripled.marsrover.business.domain.simulation;

import java.util.Optional;

public interface SimulationRepository {
    void add(Simulation simulation);

    Optional<Simulation> getSimulation();
}
