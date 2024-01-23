package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;

public interface MarsRoverApi {

    void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter);

    void landRover(Coordinate coordinate, LandingPresenter landingPresenter);

    void initializeSimulation(int simulationSize);
}
