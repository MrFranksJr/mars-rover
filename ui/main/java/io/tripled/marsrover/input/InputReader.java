package io.tripled.marsrover.input;

import io.tripled.marsrover.messages.ConsolePresenter;

import java.util.Scanner;

public class InputReader {
    private final InputHandler inputHandler ;

    public InputReader(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void readInput() {
        ConsolePresenter presenter = new ConsolePresenter();
        presenter.welcomeMessage();
        try (Scanner scanner = new Scanner(System.in)) {
            createSimWorld(scanner, presenter);
            listenForCommands(scanner, presenter);
        }
    }


    private void listenForCommands(Scanner scanner, ConsolePresenter presenter) {
        String input;
        do {
            input = scanner.nextLine();
            inputHandler.handleCommandInput(input, presenter);
        }
        while (!isQuit(input));
    }

    private void createSimWorld(Scanner scanner, ConsolePresenter presenter) {
        do {
            String input;
            input = scanner.nextLine();
            inputHandler.handleSimulationCreation(input, presenter);
        } while (!inputHandler.receivedValidSimSize);
    }

    private boolean isQuit(String input) {
        return "q".equalsIgnoreCase(input);
    }
}
