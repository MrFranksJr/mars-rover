package io.tripled.marsrover.rover;

public class Rover {
    private final String roverName;
    private RoverHeading roverHeading;
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
}
