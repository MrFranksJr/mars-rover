package io.tripled.marsrover;

import io.tripled.marsrover.commands.CommandParser;
import io.tripled.marsrover.input.InputHandler;
import io.tripled.marsrover.input.InputReader;
import io.tripled.marsrover.simulation.InMemSimulationRepo;

public class MarsRoverApplication {

    public static void main(String[] args) {
        InputReader inputReader = createReader();


        inputReader.readInput();
    }

    private static InputReader createReader() {
        InMemSimulationRepo repo = new InMemSimulationRepo();
        CommandParser commandParser = new CommandParser(repo);
        InputHandler inputHandler = new InputHandler(commandParser);
        return new InputReader(inputHandler);
    }
}
