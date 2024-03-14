package io.tripled.marsrover.cli.messages;

import io.tripled.marsrover.DTOs.RoverState;
import io.tripled.marsrover.DTOs.SimulationSnapshot;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.events.LandingOnTopEvent;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;

public class ConsolePresenter implements MessagePresenter {
    private SimulationId simId;

    public SimulationId getSimId() {
        return simId;
    }

    @Override
    public void welcomeMessage() {
        String introGraphics = """
                +----------------------------------------------------------------+
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
                +----------------------------------------------------------------+
                """;
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
    public void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot) {
        simId = simulationSnapshot.id();
        System.out.println("Simulation with max coordinate [" + simulationSnapshot.simulationSize() + "] created successfully. Simulation contains [" + simulationSnapshot.totalCoordinates() + "] coordinates");
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
    public void roverStateCommand(SimulationSnapshot simulationSnapshot) {
        System.out.println("Simulation has maxCoordinate " + simulationSnapshot.simulationSize() + " with a total of " + simulationSnapshot.totalCoordinates() + " coordinates.");
        List<RoverState> localRoverList = simulationSnapshot.roverList();
        if (localRoverList.isEmpty()) {
            System.out.println("No Rovers landed yet. Use the Land command to place a Rover in the simulation!");
        } else {
            for (RoverState rover : localRoverList) {
                System.out.println("Rover " + rover.roverId() + " at Coordinates[x=" + rover.coordinate().xCoordinate() + ", y=" + rover.coordinate().yCoordinate() + "] is facing " + rover.heading() + ". " + rover.roverId() + " has " + rover.hitpoints() + "/5 hitpoints left and is currently " + rover.healthState());
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
        System.out.println("Rover " + roverState.roverId() + " moved to new location [" + roverState.coordinate().xCoordinate() + "-" + roverState.coordinate().yCoordinate() + "]  and is now facing " + roverState.heading());
    }

    @Override
    public void roverCollidedMessage(RoverState roverState) {
        System.out.println("Rover " + roverState.roverId() + " has collided and returned to its last position");
        System.out.println("Rover " + roverState.roverId() + " is currently left with " + roverState.hitpoints() + "/5 hitpoints");
    }

    @Override
    public void roverBrokenMessage(RoverState roverState) {
        System.out.println("Rover " + roverState.roverId() + " has collided and returned to its last position");
        System.out.println("Rover " + roverState.roverId() + " is currently left with " + roverState.hitpoints() + "/5 hitpoints");
        System.out.println("Rover " + roverState.roverId() + " has broken down!!");
    }

    @Override
    public void roverAlreadyBrokenMessage(RoverId roverId) {
        System.out.println("Rover " + roverId + " can no longer move since it has already broken down");
    }

    @Override
    public void landRoversOnTopMessage(LandingOnTopEvent landingOnTopEvent) {
        System.out.println("Rover " + landingOnTopEvent.landingRoverState().roverId() + " landed on top of other Rover(s) at [" + landingOnTopEvent.coordinate().xCoordinate() + "," + landingOnTopEvent.coordinate().yCoordinate() + "] and both are now BROKEN.");
    }

    @Override
    public void roverMoveErrorMessage() {
        System.out.println("`This instruction couldn't be executed!\nTry again!");
    }
}

