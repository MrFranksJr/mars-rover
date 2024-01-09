package io.tripled.marsrover.input;

import io.tripled.marsrover.messages.ConsolePresenter;

import java.util.Scanner;

public class InputReader {
    private final InputHandler inputHandler = new InputHandler();

    public void readInput() {
        ConsolePresenter presenter = new ConsolePresenter();
        presenter.welcomeMessage();
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            do {
                createSimWorld(scanner, presenter);
                input = scanner.nextLine();
                inputHandler.handleCommandInput(input, presenter);
                //Print resultaat uit
            }
            while (!isQuit(input));
        }
    }

    private void createSimWorld(Scanner scanner, ConsolePresenter presenter) {
        String input;
        do {
            input = scanner.nextLine();
            inputHandler.handleSimulationCreation(input, presenter);
        } while (!inputHandler.receivedValidSimSize);
    }

    private boolean isQuit(String input) {
        return "q".equalsIgnoreCase(input);
    }
}
