package io.tripled.marsrover.api.simulation;

public interface SimulationCreationPresenter {
    void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot);

    void simulationCreationUnsuccessful(int simulationSize);

}
