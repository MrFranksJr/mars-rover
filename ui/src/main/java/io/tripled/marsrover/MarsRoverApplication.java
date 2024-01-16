package io.tripled.marsrover;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.cli.commands.CommandParser;
import io.tripled.marsrover.cli.input.InputHandler;
import io.tripled.marsrover.cli.input.InputReader;

public class MarsRoverApplication {

    public static void main(String[] args) {
        InputReader inputReader = createReader();
        inputReader.readInput();
    }

    private static InputReader createReader() {
        InMemSimulationRepo repo = new InMemSimulationRepo();
        MarsRoverApi api = new MarsRoverController(repo);
        CommandParser commandParser = new CommandParser(repo, api);
        InputHandler inputHandler = new InputHandler(commandParser);
        return new InputReader(inputHandler);
    }
}
