package io.tripled.marsrover;

import io.tripled.marsrover.events.RoverAlreadyBrokenEvent;
import io.tripled.marsrover.events.RoverBreaksDownEvent;
import io.tripled.marsrover.events.RoverCollidedEvent;
import io.tripled.marsrover.events.RoverMovedSuccessfulEvent;

public interface RoverMovePresenter {
    void moveRoverSuccessful(RoverMovedSuccessfulEvent r);

    void roverCollided(RoverCollidedEvent r);

    void roverBreakingDown(RoverBreaksDownEvent r);

    void roverAlreadyBrokenDown(RoverAlreadyBrokenEvent r);

    void moveRoverError(String simulationId);
}
