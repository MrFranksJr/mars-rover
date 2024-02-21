package io.tripled.marsrover.business.domain.simulation;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemSimulationRepo implements SimulationRepository {

    private Simulation simWorld;

    @Override
    public void add(Simulation simulation) {
        if (simWorld == null) {
            simWorld = simulation;
        }
    }

    @Override
    public Optional<Simulation> getSimulation() {
        if (simWorld == null)
            return Optional.empty();
        return Optional.of(simWorld);
    }
}
