package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.dbmodel.SimulationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface SimulationDocumentRepository {

    void add(Simulation simulation);

    void save(Simulation simulation);

    Optional<Simulation> getSimulation(int simulationId);
}

