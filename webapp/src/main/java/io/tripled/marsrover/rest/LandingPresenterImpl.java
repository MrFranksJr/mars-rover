package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;

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

    public RoverState reportRoverState() {
        return roverState;
    }

}
