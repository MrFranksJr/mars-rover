package io.tripled.marsrover;

public class ConsolePresenter implements MessagePresenter {
    @Override
    public void welcomeMessage() {
        System.out.println("**************************");
        System.out.println("**      Mars Rover      **");
        System.out.println("**************************");
        System.out.println("Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]");
        System.out.println("> q to quit");
    }

    @Override
    public void unknownCommand(String input) {
        System.out.println("Invalid command: [" + input + "]");
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
}

