package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.cli.messages.ConsolePresenter;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommandParser {
    private final MarsRoverApi api;

    public CommandParser(MarsRoverApi api) {
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

    private static InstructionBatch extractRoverMovesFromInput(String input) {
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        String[] roverInstructions = input.split("\\s+(?=[R])");

        for (String instructionsPerRover : roverInstructions) {
            String roverId = extractRoverIdFromInput(instructionsPerRover);

            Pattern regex = Pattern.compile("( [frbl]\\d*)");
            Matcher matcher = regex.matcher(instructionsPerRover);
            while (matcher.find()) {
                String preppedInput = matcher.group(1).trim();
                String direction = preppedInput.substring(0, 1);
                int steps = preppedInput.length() > 1 ? Integer.parseInt(preppedInput.substring(1)) : 1;
                instructionBatch.addRoverMoves(roverId, new RoverMove(direction, steps));
            }
        }
        return instructionBatch.build();
    }

    private static String extractRoverIdFromInput(String input) {
        return input.substring(0, input.indexOf(" ")).toUpperCase();
    }

    public Command parseInput(String input) {
        if (isPrintCommand(input)) return PrintCommand.INSTANCE;
        if (isQuitCommand(input)) return QuitCommand.INSTANCE;
        if (isLandCommand(input)) return handleLandCommand(input);
        if (isStateCommand(input)) return new StateCommand("1234", api);
        if (isMoveRoverCommand(input)) return handleMoveCommand(input);
        return new UnknownCommand(input);
    }

    private Command handleLandCommand(String input) {
        String trimmedLandCommandString = input.trim();
        if (isValidLandCommandInput(trimmedLandCommandString))
            return new LandCommand("1234", new Coordinate(getXCoordinateFromString(trimmedLandCommandString), getYCoordinateFromString(trimmedLandCommandString)), api);
        else return new LandingFailureCommand(trimmedLandCommandString);
    }

    private Command handleMoveCommand(String input) {
        return new RoverMoveCommand("1234", extractRoverMovesFromInput(input), api);
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
            Command quitCommand = this.parseInput(coordinate);
            quitCommand.execute(new ConsolePresenter());

        } else if (coordinate.matches("\\d+")) return Optional.of(true);
        return Optional.of(false);
    }

    private boolean isWithinLimit(int coordinate) {
        return coordinate >= 0 && coordinate <= 100;
    }

    public Optional<Command> createSimWorld(String simSizeInput) {
        if (containsOnlyNumbers(simSizeInput).orElse(false)) {
            int simSize = Integer.parseInt(simSizeInput);
            if (isWithinLimit(simSize)) return Optional.of(new SimSetupCommand(simSize, api));
        }
        return Optional.empty();
    }

    private boolean isLandCommand(String input) {
        return input.toLowerCase().startsWith("land");
    }

    private boolean isPrintCommand(String input) {
        return input.isBlank() || input.equalsIgnoreCase("p");
    }

    private boolean isMoveRoverCommand(String input) {
        Pattern pattern = Pattern.compile("R\\d+ ");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
