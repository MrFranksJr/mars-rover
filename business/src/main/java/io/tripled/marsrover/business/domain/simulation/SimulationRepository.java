package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository {
    void add(Simulation simulation);

    void save(Simulation simulation);

    Optional<List<Simulation>> retrieveSimulations();

    Optional<Simulation> getSimulation(SimulationId simulationId);
}