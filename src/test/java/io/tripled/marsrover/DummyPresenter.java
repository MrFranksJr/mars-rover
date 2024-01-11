package io.tripled.marsrover;

import io.tripled.marsrover.messages.MessagePresenter;

public class DummyPresenter implements MessagePresenter {
    private  boolean hasBeenCalled = false;
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
    public void printCommand() { }

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
    public void landRoverMessage() {

    }
}
