package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.SimulationSnapshot;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
    public Optional<Simulation> getSimulation(int simulationId) {
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
