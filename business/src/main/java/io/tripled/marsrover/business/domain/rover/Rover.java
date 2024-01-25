package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.api.RoverState;

public class Rover {
    private final String roverName;
    private RoverHeading roverHeading;
    private Location location;

    public Rover(String roverName, int xCoordinate, int yCoordinate, int simulationSize) {
        this.roverHeading = RoverHeading.NORTH;
        this.roverName = roverName;
        location = new Location(new Coordinate(xCoordinate, yCoordinate), simulationSize);
    }

    public String getRoverName() {
        return roverName;
    }
    public RoverHeading getRoverHeading() {
        return roverHeading;
    }
    public int getRoverXPosition() {
        return location.coordinate().xCoordinate();
    }
    public int getRoverYPosition() {
        return location.coordinate().yCoordinate();
    }
    public RoverState getState() {
        return new RoverState(roverName, roverHeading, location.coordinate());
    }

    public void moveForward() {
        switch (getRoverHeading()){
            case NORTH -> location = location.incrementY();
            case EAST -> location = location.incrementX();
            case SOUTH -> location = location.decrementY();
            case WEST -> location = location.decrementX();
        }
    }

    public void moveBackward() {
        switch (getRoverHeading()){
            case NORTH -> location = location.decrementY();
            case EAST -> location = location.decrementX();
            case SOUTH -> location = location.incrementY();
            case WEST -> location = location.incrementX();
        }
    }

    public void turnLeft() {
        roverHeading = roverHeading.nextCounterClockWise();;
    }

    public void turnRight() {
        roverHeading = roverHeading.nextClockWise();
    }
}
