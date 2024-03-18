package io.tripled.marsrover;

import io.tripled.marsrover.simulation.SimulationSnapshot;

import java.util.List;

public interface SimulationStatePresenter {
    void simulationState(List<SimulationSnapshot> simulationSnapshots);

    void simulationState(SimulationSnapshot simulationSnapshot);
}
