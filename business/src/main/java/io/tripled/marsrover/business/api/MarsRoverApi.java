package io.tripled.marsrover.business.api;

public interface MarsRoverApi {
    RoverState landRover(int xCoordinate, int yCoordinate);
}
