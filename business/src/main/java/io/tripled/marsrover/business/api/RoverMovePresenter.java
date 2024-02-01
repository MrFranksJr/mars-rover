package io.tripled.marsrover.business.api;

public interface RoverMovePresenter {

    void moveRoverSuccessful(RoverState roverState);
    void cannotMoveIfRoverDoesNotExist();
}
