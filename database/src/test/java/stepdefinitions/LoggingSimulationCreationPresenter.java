package stepdefinitions;

import io.tripled.marsrover.business.api.SimulationCreationPresenter;
import io.tripled.marsrover.business.api.SimulationSnapshot;

public enum LoggingSimulationCreationPresenter implements SimulationCreationPresenter {
    INSTANCE;

    @Override
    public void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot) {

    }

    @Override
    public void simulationCreationUnsuccessful(int simulationSize) {

    }

}
