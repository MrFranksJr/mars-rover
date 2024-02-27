package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.dbmodel.SimulationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SimulationDocumentRepository extends MongoRepository<SimulationDocument, UUID> {
    public void save(Simulation simulation);
}
