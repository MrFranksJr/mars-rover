package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.api.simulation.SimulationCreationPresenter;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;

public enum LoggingSimulationCreationPresenter implements SimulationCreationPresenter {
    INSTANCE;

    @Override
    public void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot) {

    }

    @Override
    public void simulationCreationUnsuccessful(int simulationSize) {

    }

}
