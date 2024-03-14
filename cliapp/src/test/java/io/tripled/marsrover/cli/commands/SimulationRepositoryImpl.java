package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DTOs.SimulationSnapshot;
import io.tripled.marsrover.SimulationQuery;
import io.tripled.marsrover.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.Optional;

public class SimulationRepositoryImpl implements SimulationRepository, SimulationQuery {

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
        if(simulationSnapshot == null)
            return Optional.empty();
        else
            return Optional.of(List.of(simulationSnapshot));
    }

    @Override
    public Optional<SimulationSnapshot> getSimulationSnapshot(SimulationId simulationId) {
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
