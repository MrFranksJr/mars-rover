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
    public RoverHeading getRoverHeading() {
        return roverHeading;
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

    public void moveForward() {
        switch (getRoverHeading()){
            case NORTH -> yPosition++;
            case EAST -> xPosition++;
            case SOUTH -> yPosition--;
            case WEST -> xPosition--;
        }
    }

    public void moveBackward() {
        switch (getRoverHeading()){
            case NORTH -> yPosition--;
            case EAST -> xPosition--;
            case SOUTH -> yPosition++;
            case WEST -> xPosition++;
        }
    }

    public void turnLeft() {
        roverHeading = roverHeading.nextCounterClockWise();;
    }

    public void turnRight() {
        roverHeading = roverHeading.nextClockWise();
    }
}
