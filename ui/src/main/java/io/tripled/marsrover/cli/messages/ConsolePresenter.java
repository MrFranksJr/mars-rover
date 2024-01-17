package io.tripled.marsrover.cli.messages;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.business.domain.rover.Rover;

import java.util.List;

public class ConsolePresenter implements MessagePresenter {
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
        System.out.println("*   Quit the application          | {Q}                                                                                                           *");
        System.out.println("*   Print API overview            | {P}                                                                                                           *");
        System.out.println("***************************************************************************************************************************************************");
    }

    @Override
    public void quitMessage() {
        System.out.println("Quiting application...");
    }

    @Override
    public void simSetupMessage(int maxCoordinate, int simSize) {
        System.out.println("Simulation with max coordinate [" + maxCoordinate + "] created successfully. Simulation contains [" + simSize + "] coordinates");
    }

    @Override
    public void invalidSimSetupMessage(String maxCoordinate) {
        System.out.println("[" + maxCoordinate + "] is an invalid Simulation maxCoordinate");
    }

    @Override
    public void landRoverMessage(RoverState roverState) {
        System.out.println("Rover " + roverState.roverName() + " landed at [" + roverState.xPosition() + "," + roverState.yPosition() + "] and is facing NORTH");
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
    public void stateCommand(SimulationState simulationState) {
        System.out.println("Simulation has maxCoordinate " + simulationState.simulationSize() + " with a total of " + simulationState.totalCoordinates() + " coordinates.");
        List<Rover> localRoverList = simulationState.roverList();
        if (localRoverList.isEmpty()) {
            System.out.println("No Rovers landed yet. Use the Land command to place a Rover in the simulation!");
        } else {
            for (Rover rover : localRoverList) {
                System.out.println("Rover " + rover.getRoverName() + " at Coordinates[x=" + rover.getRoverXPosition() +", y=" + rover.getRoverYPosition() + "] is facing NORTH");
            }
        }
    }

    @Override
    public void roverMissesSimulation(int xCoordinate, int yCoordinate, int simulationSize) {
        System.out.println("Oh no! The rover misses the simulation completely!");
        System.out.println("The coordinate [" + xCoordinate + "," + yCoordinate + "] is not a valid coordinate for the planet with max coordinate " + simulationSize);
    }

    @Override
    public void simulationAlreadyPopulated(RoverState roverState) {
        System.out.println("There is already a rover " + roverState.roverName() + " present on this planet at coordinates [" + roverState.xPosition() + "," + roverState.yPosition() + "].");
        System.out.println("Cannot land additional Rovers on this Simulation");
    }





    private final String introGraphics = """
            *********************************************************************************************************************************
            88b           d88                                                 88888888ba                                                   \s
            888b         d888                                                 88      "8b                                                  \s
            88`8b       d8'88                                                 88      ,8P                                                  \s
            88 `8b     d8' 88  ,adPPYYba,  8b,dPPYba,  ,adPPYba,              88aaaaaa8P'  ,adPPYba,   8b       d8   ,adPPYba,  8b,dPPYba, \s
            88  `8b   d8'  88  ""     `Y8  88P'   "Y8  I8[    ""              88""\""88'   a8"     "8a  `8b     d8'  a8P_____88  88P'   "Y8 \s
            88   `8b d8'   88  ,adPPPPP88  88           `"Y8ba,               88    `8b   8b       d8   `8b   d8'   8PP""\"""\""  88         \s
            88    `888'    88  88,    ,88  88          aa    ]8I              88     `8b  "8a,   ,a8"    `8b,d8'    "8b,   ,aa  88         \s
            88     `8'     88  `"8bbdP"Y8  88          `"YbbdP"'              88      `8b  `"YbbdP"'       "8"       `"Ybbd8"'  88         \s
                                                                                                                                           \s
                                                                                                                                           \s
                                                                                                                                           \s
             ad88888ba   88  88b           d88  88        88  88                  db    888888888888  88    ,ad8888ba,    888b      88     \s
            d8"     "8b  88  888b         d888  88        88  88                 d88b        88       88   d8"'    `"8b   8888b     88     \s
            Y8,          88  88`8b       d8'88  88        88  88                d8'`8b       88       88  d8'        `8b  88 `8b    88     \s
            `Y8aaaaa,    88  88 `8b     d8' 88  88        88  88               d8'  `8b      88       88  88          88  88  `8b   88     \s
              `""\"""8b,  88  88  `8b   d8'  88  88        88  88              d8YaaaaY8b     88       88  88          88  88   `8b  88     \s
                    `8b  88  88   `8b d8'   88  88        88  88             d8""\"""\"""8b    88       88  Y8,        ,8P  88    `8b 88     \s
            Y8a     a8P  88  88    `888'    88  Y8a.    .a8P  88            d8'        `8b   88       88   Y8a.    .a8P   88     `8888     \s
             "Y88888P"   88  88     `8'     88   `"Y8888Y"'   88888888888  d8'          `8b  88       88    `"Y8888Y"'    88      `888     \s
            *********************************************************************************************************************************
            """;
}

