package io.tripled.marsrover;

import java.util.Scanner;

public class InputReader {
    private final InputHandler inputHandler = new InputHandler();
    public void readInput() {
        System.out.println("> q to quit");
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            do {
                input = scanner.nextLine();
                ConsolePresenter presenter = new ConsolePresenter();
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
