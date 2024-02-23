package io.tripled.marsrover.presenters;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;

public class LandingPresenterImpl implements LandingPresenter {
    LandingState landingState = LandingState.NONE;

    @Override
    public void landingSuccessfulRefactor(RoverState roverState){
        landingState = LandingState.SUCCESS;
    }

    @Override
    public void roverMissesSimulation(int simulationSize, Coordinate coordinate) {
        landingState = LandingState.MISSES;
    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {

    }

    @Override
    public void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent) {
        landingState = LandingState.ON_TOP;
    }

    public LandingState reportLandingState() {
        return landingState;
    }
}
