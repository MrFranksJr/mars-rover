package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.api.RoverState;

public class Rover {
    private final String roverName;
    private RoverHeading roverHeading;
    private int xPosition;
    private int yPosition;

    public Rover(String roverName, int xCoordinate, int yCoordinate) {
        this.roverHeading = RoverHeading.NORTH;
        this.roverName = roverName;
        this.xPosition = xCoordinate;
        this.yPosition = yCoordinate;
    }

    public String getRoverName() {
        return roverName;
    }
    public String getRoverHeading() {
        return roverHeading.toString();
    }
    public int getRoverXPosition() {
        return xPosition;
    }
    public int getRoverYPosition() {
        return yPosition;
    }
    public RoverState getState() {
        return new RoverState(roverName, roverHeading, xPosition, yPosition);
    }
}
