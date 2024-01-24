package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.RoverMove;

import java.util.List;

public interface RoverMovePresenter {

    void moveRoverSuccessful(RoverState roverState);
}
