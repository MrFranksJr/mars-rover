package io.tripled.marsrover.business.dbmodel;

import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("MONGO")
public class MongoDbSimulationRepository implements SimulationRepository {

    private final SimulationDocumentDao mongdoDao;

    public MongoDbSimulationRepository(SimulationDocumentDao mongdoDao) {
        this.mongdoDao = mongdoDao;
    }

    private static Simulation map(SimulationDocument x) {
        return Simulation.newBuilder()
                .withId(x.getId())
                .withSimulationSize(x.getSimulationSize())
                .withRoverLocations(x.getRoverList())
                .build();
    }

    @Override
    public void add(Simulation simulation) {
        SimulationDocument s = new SimulationDocument(simulation);
        mongdoDao.save(s);
    }

    @Override
    public void save(Simulation simulation) {
        SimulationDocument s = new SimulationDocument(simulation);
        mongdoDao.save(s);
    }

    @Override
    public Optional<Simulation> getSimulation(SimulationId simulationId) {
        return mongdoDao.findById(simulationId.toString()).map(MongoDbSimulationRepository::map);
    }
}
