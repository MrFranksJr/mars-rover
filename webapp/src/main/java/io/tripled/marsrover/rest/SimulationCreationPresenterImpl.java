package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.SimulationCreationPresenter;
import io.tripled.marsrover.business.api.SimulationState;

public class SimulationCreationPresenterImpl implements SimulationCreationPresenter {

    private SimulationState simulationState;
    @Override
    public void simulationCreationSuccessful(SimulationState simulationState) {
        this.simulationState = simulationState;
    }

    @Override
    public void simulationCreationUnsuccessful(int simulationSize) {

    }

    @Override
    public void simulationAlreadyExists(SimulationState simulationState) {
        this.simulationState = simulationState;
    }

    SimulationState reportSimulationState(){
        return simulationState;
    }
}
