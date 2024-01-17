package io.tripled.marsrover.business.api;

public interface LandingPresenter {
    void landingSuccessful(RoverState state);

    void roverMissesSimulation(int simulationSize);

    void negativeCoordinatesReceived(int x, int y);

    void simulationAlreadyPopulated(RoverState roverState);
}
