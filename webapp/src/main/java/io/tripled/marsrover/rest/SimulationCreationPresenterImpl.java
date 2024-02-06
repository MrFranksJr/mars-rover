package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.SimulationCreationPresenter;
import io.tripled.marsrover.business.api.SimulationState;
import org.springframework.http.ResponseEntity;

public class SimulationCreationPresenterImpl implements SimulationCreationPresenter {

    private SimulationState simulationState;
    @Override
    public void simulationCreationSuccessful(SimulationState simulationState) {
        this.simulationState = simulationState;
    }

    @Override
    public void simulationCreationUnsuccessful(int simulationSize) {

    }

    SimulationState reportSimulationState(){
        return simulationState;
    }
}
