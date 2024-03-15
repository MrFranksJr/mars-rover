package io.tripled.marsrover.presenters;

import io.tripled.marsrover.LandingPresenter;
import io.tripled.marsrover.events.LandingOnTopEvent;
import io.tripled.marsrover.events.LandingSuccessfulLandEvent;
import io.tripled.marsrover.events.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

public class LandingPresenterImpl implements LandingPresenter {
    private LandingState landingState = LandingState.NONE;
    private SimulationId simulationId;
    private RoverId roverId;

    @Override
    public void landingSuccessful(LandingSuccessfulLandEvent landingSuccessfulLandEvent) {
        landingState = LandingState.SUCCESS;
        simulationId = landingSuccessfulLandEvent.id();
        roverId = landingSuccessfulLandEvent.roverState().roverId();
    }

    @Override
    public void roverMissesSimulation(RoverMissesSimulationLandEvent roverMissesSimulationLandEvent) {
        landingState = LandingState.MISSES;
        simulationId = roverMissesSimulationLandEvent.id();
    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {
    }

    @Override
    public void landingOnTop(LandingOnTopEvent landingOnTopEvent) {
        landingState = LandingState.ON_TOP;
        simulationId = landingOnTopEvent.id();
        roverId = landingOnTopEvent.landingRoverState().roverId();
    }

    public LandingState reportLandingState() {
        return landingState;
    }

    public SimulationId simulationId() {
        return simulationId;
    }

    public RoverId roverId() {
        return roverId;
    }
}
