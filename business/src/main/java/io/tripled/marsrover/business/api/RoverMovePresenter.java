package io.tripled.marsrover.business.api;

import io.tripled.marsrover.vocabulary.RoverId;

public interface RoverMovePresenter {
    void moveRoverSuccessful(RoverState roverState);
    void roverCollided(RoverState roverState);
    void roverBreakingDown(RoverState roverState);
    void roverAlreadyBrokenDown(RoverId roverId);
}
