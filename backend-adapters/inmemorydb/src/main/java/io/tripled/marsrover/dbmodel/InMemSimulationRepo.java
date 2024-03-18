package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.*;

public class InMemSimulationRepo implements SimulationRepository, SimulationQuery {
    private final Map<SimulationId, SimulationSnapshot> simulationMap = new HashMap<>();
    private SimulationSnapshot simulationSnapshot;

    @Override
    public void add(SimulationSnapshot snapshot) {
        simulationMap.put(snapshot.id(), snapshot);

        simulationSnapshot = snapshot;
    }

    @Override
    public void save(SimulationSnapshot snapshot) {
        simulationMap.put(snapshot.id(), snapshot);
        simulationSnapshot = snapshot;
    }

    @Override
    public Optional<List<SimulationSnapshot>> getSimulationSnapshots() {
        if (simulationMap.isEmpty()) {
            return Optional.empty();
        } else {
            List<SimulationSnapshot> simulationSnapshots = new ArrayList<>();
            for (Map.Entry<SimulationId, SimulationSnapshot> simulationMapEntry : simulationMap.entrySet()) {
                simulationSnapshots.add(simulationMapEntry.getValue());
            }
            return Optional.of(simulationSnapshots);
        }
    }

    public Optional<SimulationSnapshot> getSimulation(SimulationId simulationId) {
        if (simulationSnapshot == null)
            return Optional.empty();

        return Optional.of(simulationSnapshot);
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
