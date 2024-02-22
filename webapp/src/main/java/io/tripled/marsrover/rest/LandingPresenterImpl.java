package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;

public class LandingPresenterImpl implements LandingPresenter {


    private RoverState roverState;

    @Override
    public void landingSuccessful(RoverState roverState) {
        this.roverState = roverState;
    }

    @Override
    public void roverMissesSimulation(int simulationSize) {

    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {

    }

    @Override
    public void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent) {
        this.roverState = roverState;
    }

    public RoverState reportRoverState() {
        return roverState;
    }

}
