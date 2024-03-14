package io.tripled.marsrover;

import io.tripled.marsrover.DTOs.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository {
    void add(SimulationSnapshot snapshot);

    void save(SimulationSnapshot snapshot);

    Optional<List<SimulationSnapshot>> getSimulationSnapshots();

    Optional<SimulationSnapshot> getSimulationSnapshot(SimulationId simulationId);
}