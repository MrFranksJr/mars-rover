package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.vocabulary.SimulationId;

public class LandingPresenterImpl implements LandingPresenter {

    private MessagePresenter messagePresenter;

    public LandingPresenterImpl(MessagePresenter messagePresenter){
        this.messagePresenter = messagePresenter;
    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {
        String coordinateString = coordinate.xCoordinate() + " " + coordinate.yCoordinate();
        messagePresenter.landingFailureCommand(coordinateString, LandingErrorTypes.NEGATIVE_INTS);
    }

    @Override
    public void landingOnTop(Simulation.LandingOnTopEvent landingOnTopEvent) {
        messagePresenter.landRoversOnTopMessage(landingOnTopEvent);
    }

    @Override
    public void landingSuccessful(Simulation.LandingSuccessfulLandEvent landingSuccessfulLandEvent) {
        messagePresenter.landRoverMessage(landingSuccessfulLandEvent.roverState());
    }

    @Override
    public void roverMissesSimulation(Simulation.RoverMissesSimulationLandEvent roverMissesSimulationLandEvent) {
        messagePresenter.roverMissesSimulation(roverMissesSimulationLandEvent.coordinate().xCoordinate(), roverMissesSimulationLandEvent.coordinate().yCoordinate(), roverMissesSimulationLandEvent.simulationSize());
    }
}