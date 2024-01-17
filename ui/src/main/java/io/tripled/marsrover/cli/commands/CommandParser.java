package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.cli.messages.ConsolePresenter;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private final SimulationRepository repo;
    private final MarsRoverApi api;

    public CommandParser(SimulationRepository repo, MarsRoverApi api) {
        this.repo = repo;
        this.api = api;
    }

    private static int getXCoordinateFromString(String landString) {
        String[] splitString = landString.split("\\s+");
        String xCoordinate = splitString.length > 1 ? splitString[1] : "";
        return Integer.parseInt(xCoordinate);
    }

    private static int getYCoordinateFromString(String landString) {
        String[] splitString = landString.split("\\s+");
        String yCoordinate = splitString.length > 2 ? splitString[2] : "";
        return Integer.parseInt(yCoordinate);
    }

    public Command parseInput(String input) {
        if (isPrintCommand(input)) {
            return PrintCommand.INSTANCE;
        } else if (isQuitCommand(input)) {
            return QuitCommand.INSTANCE;
        } else if (isLandCommand(input)) {
            if (isValidLandCommandInput(input))
                return new LandCommand(getXCoordinateFromString(input), getYCoordinateFromString(input), api);
            else {
                return new LandingFailureCommand(input);
            }
        } else if (isStateCommand(input)) {
            return new StateCommand(repo);
        }
        return new UnknownCommand(input);
    }

    private boolean isStateCommand(String input) {
        return input.trim().equalsIgnoreCase("state");
    }

    private boolean isValidLandCommandInput(String input) {
        String pattern = "(?i)land\\s+(\\d+)\\s+(\\d+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }

    private boolean isQuitCommand(String input) {
        return input.equalsIgnoreCase("q");
    }

    private Optional<Boolean> containsOnlyNumbers(String coordinate) {
        if ("q".equalsIgnoreCase(coordinate)) {
            //WHAT TO DO?
            Command quitCommand = this.parseInput(coordinate);
            quitCommand.execute(new ConsolePresenter());

        } else if (coordinate.matches("\\d+")) {
            return Optional.of(true);
        }
        return Optional.of(false);
    }

    private boolean isWithinLimit(int coordinate) {
        return coordinate >= 0 && coordinate <= 100;
    }

    public Optional<Command> createSimWorld(String simSizeInput) {
        if (containsOnlyNumbers(simSizeInput).orElse(false)) {
            int simSize = Integer.parseInt(simSizeInput);
            if (isWithinLimit(simSize)) {
                return Optional.of(new SimSetupCommand(simSize, repo));
            }
        }
        return Optional.empty();
    }

    private boolean isLandCommand(String input) {
        return input.toLowerCase().startsWith("land");
    }

    private boolean isPrintCommand(String input) {
        return input.isBlank() || input.equalsIgnoreCase("p");
    }
}