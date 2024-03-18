package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.api.simulation.LandingPresenter;
import io.tripled.marsrover.api.rover.LandingOnTopEvent;
import io.tripled.marsrover.api.rover.LandingSuccessfulLandEvent;
import io.tripled.marsrover.api.rover.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.vocabulary.Coordinate;

public enum LoggingLandingPresenter implements LandingPresenter {
    INSTANCE;

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {

    }

    @Override
    public void landingOnTop(LandingOnTopEvent landingOnTopEvent) {

    }

    @Override
    public void landingSuccessful(LandingSuccessfulLandEvent landingSuccessfulLandEvent) {

    }

    @Override
    public void roverMissesSimulation(RoverMissesSimulationLandEvent r) {

    }

}
