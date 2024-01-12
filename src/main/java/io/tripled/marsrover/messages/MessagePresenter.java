package io.tripled.marsrover.messages;

import io.tripled.marsrover.commands.LandingErrorTypes;
import io.tripled.marsrover.rover.Rover;
import io.tripled.marsrover.simulation.SimulationRepository;

public interface MessagePresenter {
    void welcomeMessage();
    void unknownCommand(String input);
    void printCommand();
    void quitMessage();
    void simSetupMessage(int maxCoordinate, int simSize);
    void invalidSimSetupMessage(String maxCoordinate);
    void landRoverMessage(int landingPosX, int landingPosY, Rover rover);
    void landingFailureCommand(String coordinateString, LandingErrorTypes landingError);
    void stateCommand(SimulationRepository simRepo);
    void roverMissesSimulation(int xCoordinate, int yCoordinate, SimulationRepository simRepo);
}
