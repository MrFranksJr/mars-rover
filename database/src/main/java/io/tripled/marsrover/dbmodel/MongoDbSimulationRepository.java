package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.DTOs.SimulationSnapshot;
import io.tripled.marsrover.SimulationRepository;
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
        return SimulationSnapshot.newBuilder()
                .withId(x.getId())
                .withSimSize(x.getSimulationSize())
                .withTotalCoordinates((int) Math.pow(x.getSimulationSize() + 1, 2))
                .withRoverList(x.getRoverList())
                .build();
    }

    @Override
    public void add(SimulationSnapshot snapshot) {
        SimulationDocument s = new SimulationDocument(snapshot);

        mongoDbDao.save(s);
    }

    @Override
    public void save(SimulationSnapshot snapshot) {
        SimulationDocument s = new SimulationDocument(snapshot);
        mongoDbDao.save(s);
    }

    @Override
    public Optional<List<SimulationSnapshot>> getSimulationSnapshots() {
        List<SimulationSnapshot> retrievedSimulationSnapShots = mongoDbDao.findAll()
                .stream()
                .map(MongoDbSimulationRepository::map)
                .toList();

        if (retrievedSimulationSnapShots.isEmpty())
            return Optional.empty();
        else
            return Optional.of(retrievedSimulationSnapShots);
    }

    @Override
    public Optional<SimulationSnapshot> getSimulationSnapshot(SimulationId simulationId) {
        return mongoDbDao.findById(simulationId.toString()).map(MongoDbSimulationRepository::map);
    }
}
