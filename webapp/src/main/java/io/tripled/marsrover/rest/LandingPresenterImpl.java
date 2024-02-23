package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;

public class LandingPresenterImpl implements LandingPresenter {
    private RoverState roverState;
    private boolean landingOnTop = false;
    private boolean missesSimulation = false;
    private boolean successfulLanding = false;

    public boolean isLandingOnTop() {
        return landingOnTop;
    }
    public boolean hasMissedSimulation() {
        return missesSimulation;
    }
    @Override
    public void landingSuccessful(RoverState roverState) {
        successfulLanding = true;
        this.roverState = roverState;
    }

    @Override
    public void roverMissesSimulation(int simulationSize) {
        this.missesSimulation = true;
    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {

    }

    @Override
    public void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent) {
        this.landingOnTop = true;
    }


    public boolean hasLandedSuccessfully() {
        return successfulLanding;
    }
}
