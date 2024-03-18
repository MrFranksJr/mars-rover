package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.api.rover.RoverMovePresenter;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.RoverMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RoverMoveCommandTest {
    private MarsRoverApi mockMarsRoverApi;
    private RoverId R1;
    private UUID testUUID = UUID.randomUUID();

    @BeforeEach
    void init() {
        this.R1 = new RoverId("R1");
        mockMarsRoverApi = mock(MarsRoverApi.class);
    }

    @Test
    void simpleRoverMoveInvocation() {
        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("f", 1)))
                .build();
        final Command roverMoveCommand = new RoverMoveCommand(testUUID.toString(), instructionBatch, mockMarsRoverApi);

        roverMoveCommand.execute(new DummyPresenter());

        verify(mockMarsRoverApi).executeMoveInstructions(eq(testUUID.toString()), eq(instructionBatch), any(RoverMovePresenter.class));
    }
}