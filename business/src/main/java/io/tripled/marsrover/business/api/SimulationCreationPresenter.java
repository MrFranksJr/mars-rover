package io.tripled.marsrover.business.api;

public interface SimulationCreationPresenter {
    void simulationCreationSuccessful(SimulationState simulationState);

    void simulationCreationUnsuccessful(int simulationSize);
}
