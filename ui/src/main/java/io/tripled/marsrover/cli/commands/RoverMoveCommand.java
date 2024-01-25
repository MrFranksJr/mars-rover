package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.List;
import java.util.Objects;

public class RoverMoveCommand implements Command {
    private List<RoverMove> roverMovesFromString;
    private MarsRoverApi marsRoverApi;

    public RoverMoveCommand(List<RoverMove> roverMovesFromString, MarsRoverApi marsRoverApi) {
        this.roverMovesFromString = roverMovesFromString;
        this.marsRoverApi = marsRoverApi;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.moveRover(roverMovesFromString, new RoverMovePresenter() {
            @Override
            public void moveRoverSuccessful(RoverState roverState) {
                messagePresenter.roverMovedMessage(roverState);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoverMoveCommand that = (RoverMoveCommand) o;
        return Objects.equals(roverMovesFromString, that.roverMovesFromString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roverMovesFromString);
    }
}
