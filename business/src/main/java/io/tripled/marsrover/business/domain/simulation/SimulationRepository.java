package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository {
    void add(Simulation simulation);

    void save(Simulation simulation);

    Optional<List<SimulationSnapshot>> retrieveSimulations();

    Optional<SimulationSnapshot> getSimulation(SimulationId simulationId);
}