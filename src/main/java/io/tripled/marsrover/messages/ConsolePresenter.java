package io.tripled.marsrover.messages;

public class ConsolePresenter implements MessagePresenter {
    @Override
    public void welcomeMessage() {
        System.out.println("**************************");
        System.out.println("**      Mars Rover      **");
        System.out.println("**************************");
        System.out.println("Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]");
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
    public void simSetupCommand(int maxCoordinate, int simCoordinates) {
        System.out.println("Simulation with max coordinate [" + maxCoordinate + "] created successfully. Simulation contains [" + simCoordinates + "] coordinates");
    }

    @Override
    public void invalidSimSetupCommand(int maxCoordinate) {
        System.out.println("[" + maxCoordinate + "] is an invalid Simulation maxCoordinate");
    }
}

