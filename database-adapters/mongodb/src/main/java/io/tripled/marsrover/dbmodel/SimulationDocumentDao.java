package io.tripled.marsrover.dbmodel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationDocumentDao extends MongoRepository<SimulationDocument, String> {}
