package io.tripled.marsrover.business;

import io.tripled.marsrover.simulation.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository {
    void add(SimulationSnapshot snapshot);

    void save(SimulationSnapshot snapshot);

    Optional<List<SimulationSnapshot>> getSimulationSnapshots();

    Optional<SimulationSnapshot> getSimulation(SimulationId simulationId);
}