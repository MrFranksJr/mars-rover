package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.SimulationCreationPresenter;
import io.tripled.marsrover.business.api.SimulationSnapshot;

public class SimulationCreationPresenterImpl implements SimulationCreationPresenter {

    private SimulationSnapshot simulationSnapshot;
    @Override
    public void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot) {
        this.simulationSnapshot = simulationSnapshot;
    }

    @Override
    public void simulationCreationUnsuccessful(int simulationSize) {

    }

    @Override
    public void simulationAlreadyExists(SimulationSnapshot simulationSnapshot) {
        this.simulationSnapshot = simulationSnapshot;
    }

    SimulationSnapshot reportSimulationState(){
        return simulationSnapshot;
    }
}
