package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.api.simulation.SimulationStatePresenter;

import java.util.List;

public enum DummySimulationStatePresenter implements SimulationStatePresenter {
    INSTANCE;

    @Override
    public void simulationState(List<SimulationSnapshot> simulationSnapshots) {

    }

    @Override
    public void simulationState(SimulationSnapshot simulationSnapshot) {

    }
}
