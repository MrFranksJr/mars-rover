package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.LandingPresenter;
import io.tripled.marsrover.events.LandingOnTopEvent;
import io.tripled.marsrover.events.LandingSuccessfulLandEvent;
import io.tripled.marsrover.events.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;

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
