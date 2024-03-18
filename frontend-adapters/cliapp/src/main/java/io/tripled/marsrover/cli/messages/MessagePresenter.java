package io.tripled.marsrover.cli.messages;

import io.tripled.marsrover.api.rover.RoverState;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.api.rover.LandingOnTopEvent;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

public interface MessagePresenter {
    void welcomeMessage();

    void unknownCommand(String input);

    void printCommand();

    void quitMessage();

    void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot);

    void simulationCreationUnsuccessful(String simulationSize);

    void landRoverMessage(RoverState roverState);

    void landingFailureCommand(String coordinateString, LandingErrorTypes landingError);

    void roverStateCommand(SimulationSnapshot simulationSnapshot);

    void roverMissesSimulation(int xCoordinate, int yCoordinate, int simulationSize);

    void roverMovedMessage(RoverState roverState);

    void roverCollidedMessage(RoverState roverState);

    void roverBrokenMessage(RoverState roverState);

    void roverAlreadyBrokenMessage(RoverId roverId);

    void landRoversOnTopMessage(LandingOnTopEvent landingOnTopEvent);

    void roverMoveErrorMessage();

    SimulationId getSimId();
}