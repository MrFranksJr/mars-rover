package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public class MongoDbSimulationRepository implements SimulationRepository, SimulationQuery {
    private final SimulationDocumentDao mongoDbDao;

    public MongoDbSimulationRepository(SimulationDocumentDao mongoDbDao) {
        this.mongoDbDao = mongoDbDao;
    }

    private static SimulationSnapshot map(SimulationDocument x) {
        return SimulationSnapshot.newBuilder()
                .withId(x.getId())
                .withName(x.getSimulationName())
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
    public Optional<SimulationSnapshot> getSimulation(SimulationId simulationId) {
        return mongoDbDao.findById(simulationId.toString()).map(MongoDbSimulationRepository::map);
    }

    @Override
    public SimulationSnapshot getSimulationInformation(SimulationId simulationId) {
        //TODO: fix this behavior
        return SimulationSnapshot.NONE;
    }
}
