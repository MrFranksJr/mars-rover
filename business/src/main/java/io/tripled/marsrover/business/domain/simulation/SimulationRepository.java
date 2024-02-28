package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.vocabulary.SimulationId;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface SimulationRepository {
    void add(Simulation simulation);

    void save(Simulation simulation);

    Optional<Simulation> getSimulation(SimulationId simulationId);
}