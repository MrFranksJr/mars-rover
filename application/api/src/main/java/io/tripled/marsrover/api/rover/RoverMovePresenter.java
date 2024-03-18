package io.tripled.marsrover.api.rover;

public interface RoverMovePresenter {
    void moveRoverSuccessful(RoverMovedSuccessfulEvent r);

    void roverCollided(RoverCollidedEvent r);

    void roverBreakingDown(RoverBreaksDownEvent r);

    void roverAlreadyBrokenDown(RoverAlreadyBrokenEvent r);

    void moveRoverError(String simulationId);
}
