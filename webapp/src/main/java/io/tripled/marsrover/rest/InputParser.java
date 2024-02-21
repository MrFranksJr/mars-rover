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
        final String[] roverInstructions = roverMoves.split("\\s+(?=[R])"); //[R1 f2 r3] [R2 f6 b3]

        for (String instructionsPerRover : roverInstructions) { //[R1 f2 r3]
            final String[] roverInstructionAndId = instructionsPerRover.split(" "); //[R1] [f2] [r3]
            final String[] roverInstruction = Arrays.copyOfRange(roverInstructionAndId, 1, roverInstructionAndId.length);
            final String roverId = roverInstructionAndId[0]; //R1
            for (String singleInstruction : roverInstruction) { //
                constructInstructionBatch(singleInstruction, instructionBatchBuilder, roverId);
            }
            clearInstructionBatchIfInputIsInvalid(roverInstruction, instructionBatchBuilder, roverId);
        }
        return instructionBatchBuilder.build();
    }

    private static void clearInstructionBatchIfInputIsInvalid(String[] roverInstruction, InstructionBatch.Builder instructionBatchBuilder, String roverId) {
        if (roverInstruction.length != instructionBatchBuilder.getInstructionSizeOfRover(roverId)) {
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
        final Matcher matcher = regex.matcher(trimmedInstruction);
        return matcher;
    }

    private static String replaceSpacesFromRestCall(String roverMoves) {
        return roverMoves.replace("%20", " ");
    }
}
