package io.tripled.marsrover.business.api;

public interface SimulationCreationPresenter {
    void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot);

    void simulationCreationUnsuccessful(int simulationSize);

}
