package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository {
    void add(SimulationSnapshot snapshot);

    void save(SimulationSnapshot snapshot);

    Optional<List<SimulationSnapshot>> retrieveSimulations();

    Optional<SimulationSnapshot> getSimulation(SimulationId simulationId);
}