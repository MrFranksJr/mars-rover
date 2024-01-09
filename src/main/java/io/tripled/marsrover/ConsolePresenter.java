package io.tripled.marsrover;

public class ConsolePresenter implements MessagePresenter {
    @Override
    public void welcomeMessage() { System.out.println("**************************\n" + "**      Mars Rover      **\n" + "**************************\n" + "> q to quit"); }

    @Override
    public void unknownCommand(String input) {
        System.out.println("Unknown Command: " + input);
    }

    @Override
    public void printCommand() {
        System.out.println("Printed!");
    }
}

