package io.tripled.marsrover.business.stepdefinitions;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;

public enum LoggingLandingPresenter implements LandingPresenter {
    INSTANCE;

    @Override
    public void roverMissesSimulation(int simulationSize, Coordinate coordinate) {

    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {

    }

    @Override
    public void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent) {

    }

    @Override
    public void landingSuccessfulRefactor(RoverState roverState) {

    }

}
