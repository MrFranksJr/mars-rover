package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.simulation.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public class DummySimulationRepository implements SimulationRepository, SimulationQuery {
    private SimulationSnapshot simulationSnapshot;

    @Override
    public void add(SimulationSnapshot snapshot) {
        simulationSnapshot = snapshot;
    }

    @Override
    public void save(SimulationSnapshot snapshot) {
        simulationSnapshot = snapshot;
    }

    @Override
    public Optional<List<SimulationSnapshot>> getSimulationSnapshots() {
        if (simulationSnapshot == null)
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
