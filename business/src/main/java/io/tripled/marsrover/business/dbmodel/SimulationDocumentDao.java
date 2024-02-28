package io.tripled.marsrover.business.dbmodel;

import io.tripled.marsrover.vocabulary.SimulationId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SimulationDocumentDao extends MongoRepository<SimulationDocument, String> {}
