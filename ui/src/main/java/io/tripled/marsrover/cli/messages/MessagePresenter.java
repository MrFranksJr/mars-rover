package io.tripled.marsrover.cli.messages;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;

public interface MessagePresenter {
    void welcomeMessage();

    void unknownCommand(String input);

    void printCommand();

    void quitMessage();

    void simSetupMessage(int maxCoordinate, int simSize);

    void invalidSimSetupMessage(String maxCoordinate);

    void landRoverMessage(RoverState roverState);

    void landingFailureCommand(String coordinateString, LandingErrorTypes landingError);

    void stateCommand(SimulationRepository simRepo);

    void roverMissesSimulation(int xCoordinate, int yCoordinate, int simulationSize);

    void simulationAlreadyPopulated(RoverState roverState);
}