package io.tripled.marsrover;

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
        System.out.println("*********END*****************");
    }

    private boolean isQuit(String input) {
        return "q".equalsIgnoreCase(input);
    }
}
