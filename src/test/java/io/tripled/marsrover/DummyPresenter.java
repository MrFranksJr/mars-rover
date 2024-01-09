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
    public void simSetupCommand(int maxCoordinate, int simCoordinates) {

    }

    @Override
    public void invalidSimSetupCommand(int maxCoordinate) {

    }
}
