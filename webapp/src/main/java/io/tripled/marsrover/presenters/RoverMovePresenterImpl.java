package io.tripled.marsrover.presenters;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.rest.RoverMoveResult;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

public class RoverMovePresenterImpl implements RoverMovePresenter {

    private Pair<RoverId, RoverMoveResult> result = new Pair<>(null, RoverMoveResult.NONE);
    private SimulationId simulationId;

    @Override
    public void moveRoverSuccessful(Simulation.RoverMovedSuccessfulEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverState().roverId(), RoverMoveResult.SUCCESS);
    }

    @Override
    public void roverCollided(Simulation.RoverCollidedEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverState().roverId(), RoverMoveResult.COLLIDED);
    }

    @Override
    public void roverBreakingDown(Simulation.RoverBreaksDownEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverState().roverId(), RoverMoveResult.BROKEN);
    }

    @Override
    public void roverAlreadyBrokenDown(Simulation.RoverAlreadyBrokenEvent r) {
        this.simulationId = r.id();
        result = new Pair<>(r.roverId(), RoverMoveResult.ALREADY_BROKEN);
    }

    public Pair<RoverId, RoverMoveResult> reportRoverMoveResult() {
        return result;
    }

    public SimulationId getSimulationId() {
        return simulationId;
    }
}
