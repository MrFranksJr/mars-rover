package io.tripled.marsrover.rest;

import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;
import org.junit.jupiter.api.Test;

import static io.tripled.marsrover.rest.InputParser.INPUT_PARSER;
import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @Test
    void extractRoverMovesFromInput() {
        InstructionBatch instructionBatch = INPUT_PARSER.extractRoverMovesFromInput("R1%20f%20b3%20r");

        InstructionBatch expectedInstructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves("R1", new RoverMove("f", 1))
                .addRoverMoves("R1", new RoverMove("b", 3))
                .addRoverMoves("R1", new RoverMove("r", 1))
                .build();

        assertEquals(expectedInstructionBatch, instructionBatch);

    }

    @Test
    void invalidInputReturnsEmptyBatch() {
        InstructionBatch instructionBatch = INPUT_PARSER.extractRoverMovesFromInput("R1%20qsdf%20b3%20r");

        InstructionBatch expectedInstructionBatch = InstructionBatch.newBuilder()
                .build();

        assertEquals(expectedInstructionBatch, instructionBatch);

    }

    @Test
    void invalidInstructionAreIgnoredMultipleRovers() {
        InstructionBatch instructionBatch = INPUT_PARSER.extractRoverMovesFromInput("R1%20qsdf%20b3%20r%20R2%20f3");

        InstructionBatch expectedInstructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves("R2", new RoverMove("f", 3))
                .build();

        assertEquals(expectedInstructionBatch, instructionBatch);

    }

    @Test
    void validInstructionMultipleRovers() {
        String roverMoves = "R1%20f%20b3%20r%20R2%20f3%20R1%20b3";
        InstructionBatch instructionBatch = INPUT_PARSER.extractRoverMovesFromInput(roverMoves);

        replaceSpacesFromRestCall(roverMoves);

        InstructionBatch expectedInstructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves("R1", new RoverMove("f", 1))
                .addRoverMoves("R1", new RoverMove("b", 3))
                .addRoverMoves("R1", new RoverMove("r", 1))
                .addRoverMoves("R2", new RoverMove("f", 3))
                .addRoverMoves("R1", new RoverMove("b", 3))
                .build();

        assertEquals(expectedInstructionBatch, instructionBatch);

    }

    private void replaceSpacesFromRestCall(String roverMoves) {
        System.out.println(roverMoves.replace("%20", " "));
    }
}