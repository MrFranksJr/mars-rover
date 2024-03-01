package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;

public interface LandingPresenter {
    void negativeCoordinatesReceived(Coordinate coordinate);

    void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent);

    void landingSuccessful(Simulation.LandingSuccessfulLandEvent landingSuccessfulLandEvent);

    void roverMissesSimulation(Simulation.RoverMissesSimulationLandEvent r);
}
