package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.api.simulation.LandingPresenter;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.api.rover.LandingOnTopEvent;
import io.tripled.marsrover.api.rover.LandingSuccessfulLandEvent;
import io.tripled.marsrover.api.rover.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.vocabulary.Coordinate;

public class LandingPresenterImpl implements LandingPresenter {

    private MessagePresenter messagePresenter;

    public LandingPresenterImpl(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }

    @Override
    public void negativeCoordinatesReceived(Coordinate coordinate) {
        String coordinateString = coordinate.xCoordinate() + " " + coordinate.yCoordinate();
        messagePresenter.landingFailureCommand(coordinateString, LandingErrorTypes.NEGATIVE_INTS);
    }

    @Override
    public void landingOnTop(LandingOnTopEvent landingOnTopEvent) {
        messagePresenter.landRoversOnTopMessage(landingOnTopEvent);
    }

    @Override
    public void landingSuccessful(LandingSuccessfulLandEvent landingSuccessfulLandEvent) {
        messagePresenter.landRoverMessage(landingSuccessfulLandEvent.roverState());
    }

    @Override
    public void roverMissesSimulation(RoverMissesSimulationLandEvent roverMissesSimulationLandEvent) {
        messagePresenter.roverMissesSimulation(roverMissesSimulationLandEvent.coordinate().xCoordinate(), roverMissesSimulationLandEvent.coordinate().yCoordinate(), roverMissesSimulationLandEvent.simulationSize());
    }
}