package io.tripled.marsrover;

import java.util.Scanner;

public class InputHandler {
    public static void readInput() {
        System.out.println("> q to quit");
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            do {
                input = scanner.nextLine();
                System.out.println("I read :" + input);

            }
            while (!InputHandler.isQuit(input));
        }
        System.out.println("*********END*****************");
    }
    static boolean isQuit(String input) {
        return "q".equalsIgnoreCase(input);
    }
}