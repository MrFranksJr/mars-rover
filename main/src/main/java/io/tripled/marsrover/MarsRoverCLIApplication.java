package io.tripled.marsrover;

import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.cli.commands.CommandParser;
import io.tripled.marsrover.cli.input.InputHandler;
import io.tripled.marsrover.cli.input.InputReader;
import io.tripled.marsrover.inmemory.InMemSimulationRepo;

public class MarsRoverCLIApplication {

    public static void main(String[] args) {
        final MarsRoverApi marsRoverApi = new MarsRoverController(new InMemSimulationRepo());
        final CommandParser commandParser = new CommandParser(marsRoverApi);
        final InputHandler inputHandler = new InputHandler(commandParser);
        final InputReader inputReader = new InputReader(inputHandler);
        inputReader.readInput();
    }
}