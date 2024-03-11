package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("MONGO")
public class MongoDbSimulationRepository implements SimulationRepository {

    private final SimulationDocumentDao mongoDbDao;

    public MongoDbSimulationRepository(SimulationDocumentDao mongoDbDao) {
        this.mongoDbDao = mongoDbDao;
    }

    private static SimulationSnapshot map(SimulationDocument x) {
        return Simulation.newBuilder()
                .withId(x.getId())
                .withSimulationSize(x.getSimulationSize())
                .withRoverLocations(x.getRoverList())
                .build()
                .takeSnapshot();
    }

    @Override
    public void add(Simulation simulation) {
        SimulationDocument s = new SimulationDocument(simulation);

        mongoDbDao.save(s);
    }

    @Override
    public void save(Simulation simulation) {
        SimulationDocument s = new SimulationDocument(simulation);
        mongoDbDao.save(s);
    }

    @Override
    public Optional<List<SimulationSnapshot>> retrieveSimulations() {
        List<SimulationSnapshot> retrievedSimulationSnapShots = mongoDbDao.findAll().stream().map(MongoDbSimulationRepository::map).toList();
        if(retrievedSimulationSnapShots.isEmpty())
            return Optional.empty();
        else
            return Optional.of(retrievedSimulationSnapShots);
    }

    @Override
    public Optional<SimulationSnapshot> getSimulation(SimulationId simulationId) {
        return mongoDbDao.findById(simulationId.toString()).map(MongoDbSimulationRepository::map);
    }
}
