package io.tripled.marsrover.presenters;

import io.tripled.marsrover.RoverMovePresenter;
import io.tripled.marsrover.events.RoverAlreadyBrokenEvent;
import io.tripled.marsrover.events.RoverBreaksDownEvent;
import io.tripled.marsrover.events.RoverCollidedEvent;
import io.tripled.marsrover.events.RoverMovedSuccessfulEvent;
import io.tripled.marsrover.rest.RoverMoveResult;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.UUID;

public class RoverMovePresenterImpl implements RoverMovePresenter {

    private Pair<RoverId, RoverMoveResult> result = new Pair<>(null, RoverMoveResult.NONE);
    private SimulationId simulationId;

    @Override
    public void roverCollided(RoverCollidedEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverState().roverId(), RoverMoveResult.COLLIDED);
    }

    @Override
    public void roverBreakingDown(RoverBreaksDownEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverState().roverId(), RoverMoveResult.BROKEN);
    }

    @Override
    public void roverAlreadyBrokenDown(RoverAlreadyBrokenEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverId(), RoverMoveResult.ALREADY_BROKEN);
    }

    @Override
    public void moveRoverSuccessful(RoverMovedSuccessfulEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverState().roverId(), RoverMoveResult.SUCCESS);
    }

    @Override
    public void moveRoverError(String simulationId) {
        this.simulationId = new SimulationId(UUID.fromString(simulationId));
        result = new Pair<>(null, RoverMoveResult.ERROR);
    }

    public Pair<RoverId, RoverMoveResult> reportRoverMoveResult() {
        return result;
    }

    public SimulationId getSimulationId() {
        return simulationId;
    }
}
