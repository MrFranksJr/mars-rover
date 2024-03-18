package io.tripled.marsrover.business;

import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository {
    void add(SimulationSnapshot snapshot);

    void save(SimulationSnapshot snapshot);
    //TODO: to query
    Optional<List<SimulationSnapshot>> getSimulationSnapshots();

    //TODO: simulation ipv snapshot
    Optional<SimulationSnapshot> getSimulation(SimulationId simulationId);
}