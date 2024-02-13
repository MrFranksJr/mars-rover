package io.tripled.marsrover.vocabulary;

import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstructionBatchTest {

    private RoverId R1;
    private RoverId R2;

    @BeforeEach
    void setUp() {
        R1 = new RoverId(1);
        R2 = new RoverId(2);
    }

    @Test
    void happyPath() {

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove(R1, Direction.RIGHT, 2), new RoverMove(R1, Direction.FORWARD, 2), new RoverMove(R1, Direction.FORWARD, 3)))
                .addRoverMoves(R2, List.of(new RoverMove(R2, Direction.BACKWARD, 4)))
                .build();

        assertTrue(instructionBatch.batch().size() == 2);
    }

    @Test
    void roverIdsMustBeUniqueInInstructionBatch() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->
                        new InstructionBatch(
                                of(
                                        new RoverInstructions(R1, of(new RoverMove(R1, Direction.RIGHT, 2))),
                                        new RoverInstructions(R1, of(new RoverMove(R1, Direction.FORWARD, 4)))
                                )
                        ));
    }

    @Test
    void builderMergesMovesOfTheSameRover() {
        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove(R1, Direction.RIGHT, 2)))
                .addRoverMoves(R1, List.of(new RoverMove(R1, Direction.BACKWARD, 4)))
                .build();

        assertEquals(1, instructionBatch.batch().size());
        final RoverInstructions first = instructionBatch.batch().getFirst();
        assertEquals(R1, first.id());
        assertEquals(2, first.move().size());
    }
}