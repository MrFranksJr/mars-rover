package io.tripled.marsrover.business.dbmodel;

import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationQuery;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public Optional<List<SimulationSnapshot>> retrieveSimulations() {
        if(simulationSnapshot == null)
            return Optional.empty();
        else
            return Optional.of(List.of(simulationSnapshot));
    }

    public Optional<SimulationSnapshot> getSimulation(SimulationId simulationId) {
        if (simulationSnapshot == null)
            return Optional.empty();

        return Optional.of(simulationSnapshot);
    }

    @Override
    public SimulationSnapshot getSimulationInformation() {
        if (simulationSnapshot != null)
            return simulationSnapshot;

        return SimulationSnapshot.NONE;
    }
}
