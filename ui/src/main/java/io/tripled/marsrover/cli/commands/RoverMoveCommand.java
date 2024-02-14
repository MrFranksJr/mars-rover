package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.RoverMove;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.vocabulary.RoverId;

import java.util.List;
import java.util.Objects;

public class RoverMoveCommand implements Command {
    private List<RoverMove> roverMoves;
    private MarsRoverApi marsRoverApi;

    public RoverMoveCommand(List<RoverMove> roverMovesFromString, MarsRoverApi marsRoverApi) {
        this.roverMoves = roverMovesFromString;
        this.marsRoverApi = marsRoverApi;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.moveRover(roverMoves, new RoverMovePresenter() {
            @Override
            public void moveRoverSuccessful(RoverState roverState) {
                messagePresenter.roverMovedMessage(roverState);
            }

            @Override
            public void roverCollided(RoverId roverId) {

            }

        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoverMoveCommand that = (RoverMoveCommand) o;
        return Objects.equals(roverMoves, that.roverMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roverMoves);
    }
}
