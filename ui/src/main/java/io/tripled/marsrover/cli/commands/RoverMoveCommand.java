package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.vocabulary.RoverId;

import java.util.List;
import java.util.Objects;

public class RoverMoveCommand implements Command {
    private InstructionBatch roverInstructionBatch;
    private MarsRoverApi marsRoverApi;

    public RoverMoveCommand(InstructionBatch roverInstructionsFromString, MarsRoverApi marsRoverApi) {
        this.roverInstructionBatch = roverInstructionsFromString;
        this.marsRoverApi = marsRoverApi;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.executeMoveInstructions(roverInstructionBatch, new RoverMovePresenter() {
            @Override
            public void moveRoverSuccessful(RoverState roverState) {
                messagePresenter.roverMovedMessage(roverState);
            }

            @Override
            public void roverCollided(RoverId roverId) {
                messagePresenter.roverCollidedMessage(roverId);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoverMoveCommand that = (RoverMoveCommand) o;

        return Objects.equals(roverInstructionBatch, that.roverInstructionBatch);
    }

    @Override
    public int hashCode() {
        return roverInstructionBatch != null ? roverInstructionBatch.hashCode() : 0;
    }
}
