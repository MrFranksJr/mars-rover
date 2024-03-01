package io.tripled.marsrover.presenters;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

public class LandingPresenterImpl implements LandingPresenter {
    private LandingState landingState = LandingState.NONE;
    private SimulationId simulationId;
    private RoverId roverId;

    @Override
    public void landingSuccessful(Simulation.LandingSuccessfulLandEvent landingSuccessfulLandEvent){
        landingState = LandingState.SUCCESS;
        simulationId = landingSuccessfulLandEvent.id();
        roverId = landingSuccessfulLandEvent.roverState().roverId();
    }

    @Override
    public void roverMissesSimulation(Simulation.RoverMissesSimulationLandEvent roverMissesSimulationLandEvent) {
        landingState = LandingState.MISSES;
        simulationId = roverMissesSimulationLandEvent.id();
    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {
    }

    @Override
    public void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent) {
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
