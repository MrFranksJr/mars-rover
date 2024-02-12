package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;

public interface LandingPresenter {
    void landingSuccessful(RoverState state);

    void roverMissesSimulation(int simulationSize);

    void negativeCoordinatesReceived(Coordinate coordinate);
}
