package io.tripled.marsrover.messages;

import io.tripled.marsrover.rover.Rover;

public interface MessagePresenter {
    void welcomeMessage();
    void unknownCommand(String input);
    void printCommand();
    void quitMessage();
    void simSetupMessage(int maxCoordinate, int simSize);
    void invalidSimSetupMessage(String maxCoordinate);
    void landRoverMessage(int landingPosX, int landingPosY, Rover rover);
}
