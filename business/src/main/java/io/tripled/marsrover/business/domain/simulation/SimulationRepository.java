package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.SimulationSnapshot;

import java.util.Optional;

public interface SimulationRepository {
    void add(Simulation simulation);
    void save(Simulation simulationSnapshot);
    Optional<Simulation> getSimulation(int simulationId);
}