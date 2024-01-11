package io.tripled.marsrover.messages;

import io.tripled.marsrover.rover.Rover;

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
    public void landRoverMessage(int landingPosX, int landingPosY, String roverName) {
        System.out.println("[LANDING]");
    }
}

