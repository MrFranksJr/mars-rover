package io.tripled.marsrover.commands;

import io.tripled.marsrover.simulation.SimulationRepository;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private final SimulationRepository repo;

    public CommandParser(SimulationRepository repo) {
        this.repo = repo;
    }

    public Command parseInput(String input) {
        if (isPrintCommand(input)) {
            return PrintCommand.INSTANCE;
        } else if (isQuitCommand(input)) {
            return QuitCommand.INSTANCE;
        } else if (isLandCommand(input)) {
            if (isValidLandCommandInput(input))
                return new LandCommand(getXCoordinateFromString(input), getYCoordinateFromString(input));
        }
        return new UnknownCommand(input);
    }

    private boolean isValidLandCommandInput(String input) {
        String pattern = "(?i)land\\s+(\\d+)\\s+(\\d+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }

    private static boolean isQuitCommand(String input) {
        return input.equalsIgnoreCase("q");
    }

    private static boolean containsOnlyNumbers(String coordinate) {
        return coordinate.matches("\\d+");
    }

    private static boolean isWithinLimit(int coordinate) {
        return coordinate >= 0 && coordinate <= 100;
    }

    public Optional<Command> createSimWorld(String simSizeInput) {
        if (containsOnlyNumbers(simSizeInput)) {
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
}