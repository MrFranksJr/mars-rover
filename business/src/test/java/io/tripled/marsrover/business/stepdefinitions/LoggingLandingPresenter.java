package io.tripled.marsrover.business.stepdefinitions;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;

public enum LoggingLandingPresenter implements LandingPresenter {
    INSTANCE;


    @Override
    public void landingSuccessful(RoverState state) {

    }

    @Override
    public void roverMissesSimulation(int simulationSize) {

    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {

    }
}
