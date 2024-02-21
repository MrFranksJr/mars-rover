package io.tripled.marsrover.rest;

import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputParser {
    INPUT_PARSER;

    public static final String REGEX = "(^[frbl]\\d*)$";

    public InstructionBatch extractRoverMovesFromInput(String roverMoves) {
        final InstructionBatch.Builder instructionBatchBuilder = InstructionBatch.newBuilder();
        roverMoves = replaceSpacesFromRestCall(roverMoves);
        final String[] roversAndInstructions = roverMoves.split("\\s+(?=[R])");

        for (String instructionsPerRover : roversAndInstructions) {
            final String[] roverIdAndInstructions = instructionsPerRover.split(" ");
            final String roverId = roverIdAndInstructions[0];
            final String[] roverInstructions = Arrays.copyOfRange(roverIdAndInstructions, 1, roverIdAndInstructions.length);
            final int initialRoverInstructionBatchSize = instructionBatchBuilder.getInstructionSizeOfRover(roverId);

            addInstructionToBatch(roverInstructions, instructionBatchBuilder, roverId);
            final int totalNumberOfInstructions = initialRoverInstructionBatchSize + roverInstructions.length;
            clearInstructionBatchIfInputIsInvalid(totalNumberOfInstructions, instructionBatchBuilder, roverId);
        }
        return instructionBatchBuilder.build();
    }

    private static void addInstructionToBatch(String[] roverInstruction, InstructionBatch.Builder instructionBatchBuilder, String roverId) {
        for (String singleInstruction : roverInstruction) {
            constructInstructionBatch(singleInstruction, instructionBatchBuilder, roverId);
        }
    }

    private static void clearInstructionBatchIfInputIsInvalid(int totalNumberOfInstructions, InstructionBatch.Builder instructionBatchBuilder, String roverId) {
        if (totalNumberOfInstructions != instructionBatchBuilder.getInstructionSizeOfRover(roverId)) {
            instructionBatchBuilder.clearRoverMoves(roverId);
        }
    }

    private static void constructInstructionBatch(String singleInstruction, InstructionBatch.Builder instructionBatchBuilder, String roverId) {
        final Matcher matcher = createRegexMatcher(singleInstruction);
        while (matcher.find()) {
            final String preppedInput = matcher.group(1).trim();
            final String direction = preppedInput.substring(0, 1);
            final int steps = preppedInput.length() > 1 ? Integer.parseInt(preppedInput.substring(1)) : 1;
            instructionBatchBuilder.addRoverMoves(roverId, new RoverMove(direction, steps));
        }
    }

    private static Matcher createRegexMatcher(String singleInstruction) {
        final String trimmedInstruction = singleInstruction.trim();
        final Pattern regex = Pattern.compile(REGEX);
        return regex.matcher(trimmedInstruction);
    }

    private static String replaceSpacesFromRestCall(String roverMoves) {
        return roverMoves.replace("%20", " ");
    }
}
