package io.tripled.marsrover.presenters;

import io.tripled.marsrover.api.simulation.SimulationCreationPresenter;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;

public class SimulationCreationPresenterImpl implements SimulationCreationPresenter {

    private SimulationSnapshot simulationSnapshot;

    @Override
    public void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot) {
        this.simulationSnapshot = simulationSnapshot;
    }

    @Override
    public void simulationCreationUnsuccessful(int simulationSize) {

    }

    public SimulationSnapshot reportSimulationState() {
        return simulationSnapshot;
    }
}
