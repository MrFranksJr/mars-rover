package io.tripled.marsrover.business.dbmodel;

import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationQuery;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("INMEM")
public class InMemSimulationRepo implements SimulationRepository, SimulationQuery {
    private SimulationSnapshot simulationSnapshot;

    @Override
    public void add(Simulation simulation) {
        simulationSnapshot = simulation.takeSnapshot();
    }

    @Override
    public void save(Simulation simulation) {
        simulationSnapshot = simulation.takeSnapshot();
    }

    //TODO implement SimulationID
    public Optional<Simulation> getSimulation(SimulationId simulationId) {
        if (simulationSnapshot == null)
            return Optional.empty();
        return Optional.of(Simulation.of(simulationSnapshot));
    }

    @Override
    public SimulationSnapshot getSimulationInformation() {
        if (simulationSnapshot != null){
            return simulationSnapshot;
        }
        return null;
    }
}
