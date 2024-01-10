package io.tripled.marsrover.messages;

public interface MessagePresenter {
    void welcomeMessage();
    void unknownCommand(String input);
    void printCommand();
    void quitMessage();
    void simSetupMessage(int maxCoordinate, int simSize);
    void invalidSimSetupMessage(String maxCoordinate);
}
