package io.tripled.marsrover.messages;

public interface MessagePresenter {
    void welcomeMessage();
    void unknownCommand(String input);
    void printCommand();
    void quitMessage();
    void simSetupCommand(int maxCoordinate, int simCoordinates);
    void invalidSimSetupCommand(int maxCoordinate);
}
