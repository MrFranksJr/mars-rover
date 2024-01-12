package io.tripled.marsrover;

import io.tripled.marsrover.commands.LandingErrorTypes;
import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.rover.Rover;

public class DummyPresenter implements MessagePresenter {
    private boolean hasBeenCalled = false;

    public boolean hasUnknownCommandBeenInvoked() {
        return hasBeenCalled;
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
    public void landRoverMessage(int landingPosX, int landingPosY, Rover roverName) {

    }

    @Override
    public void landingFailureCommand(String coordinateString, LandingErrorTypes landingError) {

    }

    @Override
    public void stateCommand() {

    }
}
