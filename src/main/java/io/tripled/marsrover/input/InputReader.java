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
                input = scanner.nextLine();
                inputHandler.handleInput(input, presenter);
                //Print resultaat uit
            }
            while (!isQuit(input));
        }
    }

    private boolean isQuit(String input) {
        return "q".equalsIgnoreCase(input);
    }
}
