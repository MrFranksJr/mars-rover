package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.simulation.Simulation;

public interface RoverMovePresenter {
    void moveRoverSuccessful(Simulation.RoverMovedSuccessfulEvent r);

    void roverCollided(Simulation.RoverCollidedEvent r);

    void roverBreakingDown(Simulation.RoverBreaksDownEvent r);

    void roverAlreadyBrokenDown(Simulation.RoverAlreadyBrokenEvent r);

    void moveRoverError(String simulationId);
}
