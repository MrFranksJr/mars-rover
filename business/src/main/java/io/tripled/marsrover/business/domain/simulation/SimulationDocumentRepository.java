package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.dbmodel.SimulationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SimulationDocumentRepository extends MongoRepository<SimulationDocument, UUID> {

}
