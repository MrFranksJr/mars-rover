package io.tripled.marsrover.api.simulation;

import java.util.List;

public interface SimulationStatePresenter {
    void simulationState(List<SimulationSnapshot> simulationSnapshots);

    void simulationState(SimulationSnapshot simulationSnapshot);
}
