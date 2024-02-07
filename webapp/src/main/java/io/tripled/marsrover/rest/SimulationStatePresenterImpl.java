package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.api.SimulationStatePresenter;

public class SimulationStatePresenterImpl implements SimulationStatePresenter {
    private SimulationState simulationState;

    @Override
    public void simulationState(SimulationState simulationState) {
        this.simulationState = simulationState;
    }

    public SimulationState getSimulationState(){

        return simulationState;
    }
}
