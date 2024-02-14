package io.tripled.marsrover.cli.messages;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.vocabulary.RoverId;

import java.util.List;

public class ConsolePresenter implements MessagePresenter {
    private final String introGraphics = """
            +----------------------------------------------------------------+
            |                                                                |
            |      ██████   ██████                                           |
            |     ░░██████ ██████                                            |
            |      ░███░█████░███   ██████   ████████   █████                |
            |      ░███░░███ ░███  ░░░░░███ ░░███░░███ ███░░                 |
            |      ░███ ░░░  ░███   ███████  ░███ ░░░ ░░█████                |
            |      ░███      ░███  ███░░███  ░███      ░░░░███               |
            |      █████     █████░░████████ █████     ██████                |
            |     ░░░░░     ░░░░░  ░░░░░░░░ ░░░░░     ░░░░░░                 |
            |      ███████████                                               |
            |     ░░███░░░░░███                                              |
            |      ░███    ░███   ██████  █████ █████  ██████  ████████      |
            |      ░██████████   ███░░███░░███ ░░███  ███░░███░░███░░███     |
            |      ░███░░░░░███ ░███ ░███ ░███  ░███ ░███████  ░███ ░░░      |
            |      ░███    ░███ ░███ ░███ ░░███ ███  ░███░░░   ░███          |
            |      █████   █████░░██████   ░░█████   ░░██████  █████         |
            |     ░░░░░   ░░░░░  ░░░░░░     ░░░░░     ░░░░░░  ░░░░░          |
            |                                                                |
            +----------------------------------------------------------------+
            """;

    @Override
    public void welcomeMessage() {
        System.out.println(introGraphics);
        System.out.println("Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]");
        System.out.println("[Enter max coordinate] : ");
    }

    @Override
    public void unknownCommand(String input) {
        System.out.println("Invalid command: [" + input + "]");
        printCommand();
    }

    @Override
    public void printCommand() {
        System.out.println("***************************************************************************************************************************************************");
        System.out.println("*   Print state of simulation     | {state}                                                   | ex: state                                         *");
        System.out.println("*   Land a new rover              | {land {x} {y}}                                            | ex: land 1 5                                      *");
        System.out.println("*   Drive                         | {Rx fx|bx|lx|rx}                                          | ex: R1 f5 l b2                                    *");
        System.out.println("*   Quit the application          | {Q}                                                                                                           *");
        System.out.println("*   Print API overview            | {P}                                                                                                           *");
        System.out.println("***************************************************************************************************************************************************");
    }

    @Override
    public void quitMessage() {
        System.out.println("Quiting application...");
    }

    @Override
    public void simulationCreationSuccessful(SimulationState simulationState) {
        System.out.println("Simulation with max coordinate [" + simulationState.simulationSize() + "] created successfully. Simulation contains [" + simulationState.totalCoordinates() + "] coordinates");
    }

    @Override
    public void simulationCreationUnsuccessful(String simulationSize) {
        System.out.println("[" + simulationSize + "] is an invalid Simulation maxCoordinate");
    }

    @Override
    public void landRoverMessage(RoverState roverState) {
        System.out.println("Rover " + roverState.roverId() + " landed at [" + roverState.coordinate().xCoordinate() + "," + roverState.coordinate().yCoordinate() + "] and is facing NORTH");
    }

    @Override
    public void landingFailureCommand(String coordinateString, LandingErrorTypes landingError) {
        switch (landingError) {
            case NEGATIVE_INTS ->
                    System.out.println("Invalid coordinates for landing. They must be greater than zero but were [" + coordinateString + "]");
            case RECEIVED_LETTERS ->
                    System.out.println("Unable to parse coordinates for landing. Expected two positive numbers [x y] but was [" + coordinateString + "]");
            case UNABLE_TO_PARSE ->
                    System.out.println("Unable to parse coordinates for landing. Expected [x y] but was [" + coordinateString + "]");
        }
    }

    @Override
    public void roverStateCommand(SimulationState simulationState) {
        System.out.println("Simulation has maxCoordinate " + simulationState.simulationSize() + " with a total of " + simulationState.totalCoordinates() + " coordinates.");
        List<RoverState> localRoverList = simulationState.roverList();
        if (localRoverList.isEmpty()) {
            System.out.println("No Rovers landed yet. Use the Land command to place a Rover in the simulation!");
        } else {
            for (RoverState rover : localRoverList) {
                System.out.println("Rover " + rover.roverId() + " at Coordinates[x=" + rover.coordinate().xCoordinate() + ", y=" + rover.coordinate().yCoordinate() + "] is facing " + rover.roverHeading());
            }
        }
    }

    @Override
    public void roverMissesSimulation(int xCoordinate, int yCoordinate, int simulationSize) {
        System.out.println("Oh no! The rover misses the simulation completely!");
        System.out.println("The coordinate [" + xCoordinate + "," + yCoordinate + "] is not a valid coordinate for the planet with max coordinate " + simulationSize);
    }

    @Override
    public void roverMovedMessage(RoverState roverState) {
        System.out.println("Rover " + roverState.roverId() + " moved to new location [" + roverState.coordinate().xCoordinate() + "-" + roverState.coordinate().yCoordinate() + "]  and is now facing " + roverState.roverHeading());
    }

    @Override
    public void roverDoesNotExist() {
        System.out.println("Cannot moves a Rover that does not exist!");

    }

    @Override
    public void duplicateSimulationDetected(SimulationState simulationState) {
        System.out.println("There is already a simulation with size " + simulationState.simulationSize());
    }
    //TODO: RoverID of the collided rover needs to be reported here + last successful location
    @Override
    public void roverCollidedMessage(RoverId roverId) {
        System.out.println("Rover " + roverId + " has collided and returned to its last position");
    }
}

