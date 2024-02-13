package io.tripled.marsrover.business.stepdefinitions;

import io.tripled.marsrover.business.api.SimulationCreationPresenter;
import io.tripled.marsrover.business.api.SimulationState;

public enum LoggingSimulationCreationPresenter implements SimulationCreationPresenter {
    INSTANCE;

    @Override
    public void simulationCreationSuccessful(SimulationState simulationState) {

    }

    @Override
    public void simulationCreationUnsuccessful(int simulationSize) {

    }

    @Override
    public void simulationAlreadyExists(SimulationState simulationState) {

    }
}
