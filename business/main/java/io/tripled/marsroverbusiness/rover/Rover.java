package io.tripled.marsroverbusiness.rover;

public class Rover {
    private final String roverName;
    private RoverHeading roverHeading;
    private int xPosition;
    private int yPosition;

    public Rover(String roverName) {
        this.roverHeading = RoverHeading.NORTH;
        this.roverName = roverName;
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

    public void setPosition(int xCoordinate, int yCoordinate) {
        this.xPosition = xCoordinate;
        this.yPosition = yCoordinate;
    }
}
