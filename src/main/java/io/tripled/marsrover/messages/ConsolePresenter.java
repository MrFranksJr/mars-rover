package io.tripled.marsrover.messages;

import io.tripled.marsrover.commands.LandingErrorTypes;
import io.tripled.marsrover.rover.Rover;
import io.tripled.marsrover.simulation.SimulationRepository;

public class ConsolePresenter implements MessagePresenter {
    @Override
    public void welcomeMessage() {
        System.out.println("**************************");
        System.out.println("**      Mars Rover      **");
        System.out.println("**************************");
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
    public void landRoverMessage(int landingPosX, int landingPosY, Rover rover) {
        System.out.println("Rover " + rover.getRoverName() + " landed at [" + landingPosX + "," + landingPosY + "] and is facing North");
    }

    @Override
    public void landingFailureCommand(String coordinateString, LandingErrorTypes landingError) {
        switch (landingError) {
            case NEGATIVE_INTS -> System.out.println("Invalid coordinates for landing. They must be greater than zero but were [" + coordinateString + "]");
            case RECEIVED_LETTERS -> System.out.println("Unable to parse coordinates for landing. Expected two positive numbers [x y] but was [" + coordinateString + "]");
            case UNABLE_TO_PARSE -> System.out.println("Unable to parse coordinates for landing. Expected [x y] but was [" + coordinateString + "]");
        }
    }

    @Override
    public void stateCommand(SimulationRepository simRepo) {
        System.out.println("Simulation has maxCoordinate " + simRepo.getSimulation().getSimulationSize() + " with a total of " + simRepo.getSimulation().getNrOfCoordinates() + " coordinates.");
       /* if (simRepo.getSimulation().getRover != null) {
            System.out.println("Rover at Coordinates[x=1, y=1] is facing NORTH");

        }*/
    }
}

