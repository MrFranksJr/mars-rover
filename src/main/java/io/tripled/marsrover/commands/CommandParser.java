package io.tripled.marsrover.commands;

import io.tripled.marsrover.simulation.SimulationRepository;

import java.util.Optional;
import java.util.regex.*;

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
            return LandCommand.INSTANCE;
        }
        return new UnknownCommand(input);
    }

    private boolean isLandCommand(String input) {
        String pattern = "(?i)land\\s+(\\d+)\\s+(\\d+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        if (matcher.matches()) {
            int posX = Integer.parseInt(matcher.group(1));
            int posY = Integer.parseInt(matcher.group(2));

            return posX >= 0 && posX <= this.repo.getSimulation().getNrOfCoordinates()
                    && posY >= 0 && posY <= this.repo.getSimulation().getNrOfCoordinates();
        }
        return false;
    }

    private static boolean isQuitCommand(String input) {
        return input.equalsIgnoreCase("q");
    }

    private boolean isPrintCommand(String input) {
        return input.isBlank() || input.equalsIgnoreCase("p");
    }

    public  Optional<Command> createSimWorld(String simSizeInput) {
        if (containsOnlyNumbers(simSizeInput)) {
            int simSize = Integer.parseInt(simSizeInput);
            if (isWithinLimit(simSize)) {
                return Optional.of(new SimSetupCommand(simSize,repo));
            }
        }
        return Optional.empty();
    }

    private static boolean containsOnlyNumbers(String coordinate) {
        return coordinate.matches("\\d+");
    }

    private static boolean isWithinLimit(int coordinate) {
        return coordinate >= 0 && coordinate <= 100;
    }
}
