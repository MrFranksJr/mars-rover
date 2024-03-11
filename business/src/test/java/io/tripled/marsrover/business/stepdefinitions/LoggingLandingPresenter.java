package io.tripled.marsrover.business.stepdefinitions;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;

public enum LoggingLandingPresenter implements LandingPresenter {
    INSTANCE;

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {

    }

    @Override
    public void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent) {

    }

    @Override
    public void landingSuccessful(Simulation.LandingSuccessfulLandEvent landingSuccessfulLandEvent) {

    }

    @Override
    public void roverMissesSimulation(Simulation.RoverMissesSimulationLandEvent r) {

    }

}
