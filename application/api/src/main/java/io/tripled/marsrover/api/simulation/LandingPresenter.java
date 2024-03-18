package io.tripled.marsrover.api.simulation;

import io.tripled.marsrover.api.rover.LandingOnTopEvent;
import io.tripled.marsrover.api.rover.LandingSuccessfulLandEvent;
import io.tripled.marsrover.api.rover.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.vocabulary.Coordinate;

public interface LandingPresenter {
    void negativeCoordinatesReceived(Coordinate coordinate);

    void landingOnTop(LandingOnTopEvent landingOnTopEvent);

    void landingSuccessful(LandingSuccessfulLandEvent landingSuccessfulLandEvent);

    void roverMissesSimulation(RoverMissesSimulationLandEvent r);
}
