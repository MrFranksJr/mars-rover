package io.tripled.marsrover.cli.messages;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;

public interface MessagePresenter {
    void welcomeMessage();
    void unknownCommand(String input);
    void printCommand();
    void quitMessage();
    void simulationCreationSuccessful(SimulationState simulationState);
    void simulationCreationUnsuccessful(String simulationSize);
    void landRoverMessage(RoverState roverState);
    void landingFailureCommand(String coordinateString, LandingErrorTypes landingError);
    void roverStateCommand(SimulationState simulationState);
    void roverMissesSimulation(int xCoordinate, int yCoordinate, int simulationSize);
    void simulationAlreadyPopulated(RoverState roverState);
    void roverMovedMessage(RoverState roverState);
    void roverDoesNotExist();
}