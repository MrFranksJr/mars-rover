package io.tripled.marsrover.business.api;

public interface MarsRoverApi {
    void landRover(int xCoordinate, int yCoordinate, LandingPresenter landingPresenter);

    void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter);

    void initializeSimulation(int simulationSize);
}
