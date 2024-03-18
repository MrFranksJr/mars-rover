package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.*;

public class DummySimulationRepository implements SimulationRepository, SimulationQuery {
    private SimulationSnapshot simulationSnapshot;
    private final Map<SimulationId, SimulationSnapshot> simulationMap = new HashMap<>();

    @Override
    public void add(SimulationSnapshot snapshot) {
        simulationMap.put(snapshot.id(), snapshot);
    }

    @Override
    public void save(SimulationSnapshot snapshot) {
        simulationMap.put(snapshot.id(), snapshot);
    }

    @Override
    public Optional<List<SimulationSnapshot>> getSimulationSnapshots() {
        if (simulationMap.isEmpty()) {
            return Optional.empty();
        }
        else {
            List<SimulationSnapshot> simulationSnapshots = new ArrayList<>();
            for (Map.Entry<SimulationId, SimulationSnapshot> simulationMapEntry : simulationMap.entrySet()) {
                simulationSnapshots.add(simulationMapEntry.getValue());
            }
            return Optional.of(simulationSnapshots);
        }
    }

    public Optional<SimulationSnapshot> getSimulation(SimulationId simulationId) {
        if (simulationMap.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(simulationMap.get(simulationId));
        }
    }

    @Override
    public SimulationSnapshot getSimulationInformation(SimulationId simulationId) {
        if (simulationMap.isEmpty()) {
            return SimulationSnapshot.NONE;
        }
        else {
            return simulationMap.get(simulationId);
        }
    }
}
