package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverId;

import java.util.Objects;

public class RoverMoveCommand implements Command {
    private InstructionBatch roverInstructionBatch;
    private MarsRoverApi marsRoverApi;

    private String simulationId;

    public RoverMoveCommand(String simulationId, InstructionBatch roverInstructionsFromString, MarsRoverApi marsRoverApi) {
        this.roverInstructionBatch = roverInstructionsFromString;
        this.marsRoverApi = marsRoverApi;
        this.simulationId = simulationId;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.executeMoveInstructions(simulationId, roverInstructionBatch, new RoverMovePresenter() {
            @Override
            public void moveRoverSuccessful(Simulation.RoverMovedSuccessfulEvent r) {
                messagePresenter.roverMovedMessage(r.roverState());
            }

            @Override
            public void roverCollided(Simulation.RoverCollidedEvent r) {
                messagePresenter.roverCollidedMessage(r.roverState());
            }

            @Override
            public void roverBreakingDown(Simulation.RoverBreaksDownEvent r) {
                messagePresenter.roverBrokenMessage(r.roverState());
            }

            @Override
            public void roverAlreadyBrokenDown(Simulation.RoverAlreadyBrokenEvent r) {
                messagePresenter.roverAlreadyBrokenMessage(r.roverId());
            }

            @Override
            public void moveRoverError(String simulationId) {
                messagePresenter.roverMoveErrorMessage();
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
