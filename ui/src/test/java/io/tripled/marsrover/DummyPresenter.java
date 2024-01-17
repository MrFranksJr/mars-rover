package io.tripled.marsrover;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.cli.messages.MessagePresenter;

public class DummyPresenter implements MessagePresenter {
    public RoverState roverState;
    public boolean hasRoverMissedSimulation = false;
    private boolean hasBeenCalled = false;
    private boolean hasRoverLanded = false;
    private boolean hasLandingFailed = false;
    private boolean alreadyRoverPresent = false;
    private boolean hadStateCommandInvoked = false;

    public boolean hasRoverLanded() {
        return hasRoverLanded;
    }


    public boolean hasUnknownCommandBeenInvoked() {
        return hasBeenCalled;
    }

    public boolean hasRoverMissedSimulationBeenInvoked() {
        return hasRoverMissedSimulation;
    }

    public boolean invalidLandingInstruction() {
        return hasLandingFailed;
    }

    @Override
    public void welcomeMessage() {

    }

    @Override
    public void unknownCommand(String input) {
        hasBeenCalled = true;
    }

    @Override
    public void printCommand() {
    }

    @Override
    public void quitMessage() {

    }

    @Override
    public void simSetupMessage(int maxCoordinate, int simSize) {

    }

    @Override
    public void invalidSimSetupMessage(String maxCoordinate) {

    }

    @Override
    public void landRoverMessage(RoverState roverState) {
        hasRoverLanded = true;
        this.roverState = roverState;
    }

    @Override
    public void landingFailureCommand(String coordinateString, LandingErrorTypes landingError) {
        hasLandingFailed = true;
    }

    @Override
    public void stateCommand(SimulationRepository simRepo) {
        hadStateCommandInvoked = true;
    }
    public boolean hasStateCommandBeenInvoked() {
        return hadStateCommandInvoked;
    }

    @Override
    public void roverMissesSimulation(int xCoordinate, int yCoordinate, int simulationSize) {
        hasRoverMissedSimulation = true;
    }

    @Override
    public void simulationAlreadyPopulated(RoverState roverState) {
        this.alreadyRoverPresent=true;
    }

    public boolean wasAlreadyRoverPresentInvoked() {
        return alreadyRoverPresent;
    }

}