package io.tripled.marsrover;

import io.tripled.marsrover.events.LandingOnTopEvent;
import io.tripled.marsrover.events.LandingSuccessfulLandEvent;
import io.tripled.marsrover.events.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.vocabulary.Coordinate;

public interface LandingPresenter {
    void negativeCoordinatesReceived(Coordinate coordinate);

    void landingOnTop(LandingOnTopEvent landingOnTopEvent);

    void landingSuccessful(LandingSuccessfulLandEvent landingSuccessfulLandEvent);

    void roverMissesSimulation(RoverMissesSimulationLandEvent r);
}
