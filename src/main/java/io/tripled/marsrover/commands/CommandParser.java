package io.tripled.marsrover.commands;

import io.tripled.marsrover.simulation.SimulationRepository;
import io.tripled.marsrover.validators.SimSizeValidator;

import java.util.Optional;

public class CommandParser {
    private final  SimulationRepository repo;

    public CommandParser(SimulationRepository repo) {
        this.repo = repo;
    }

    public Command parseInput(String input) {
        if (input.isBlank() || input.equalsIgnoreCase("p")) {
            return PrintCommand.INSTANCE;
        } else if (input.equalsIgnoreCase("q")) {
            return QuitCommand.INSTANCE;
        }
        return new UnknownCommand(input);
    }

    public  Optional<Command> createSimWorld(String maxCoordinate) {
        if (SimSizeValidator.validateMaxCoordinate(maxCoordinate)) {
            return Optional.of(new SimSetupCommand(maxCoordinate,repo));
        } else {
            return Optional.empty();
        }
    }

    public Command landRover(String landCoordinate) {
        return LandCommand.INSTANCE;
    }
}
