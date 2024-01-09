package io.tripled.marsrover;

public class DummyPresenter implements MessagePresenter{
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
}
