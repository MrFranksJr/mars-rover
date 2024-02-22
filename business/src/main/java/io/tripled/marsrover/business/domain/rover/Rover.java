package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.RoverId;

public class Rover {
    private final RoverId roverId;
    private RoverHeading roverHeading;
    private  int hitPoints;
    private OperationalStatus roverBrokenState;

    public Rover(RoverId roverId, RoverHeading roverHeading) {
        this.roverId = roverId;
        this.roverHeading = roverHeading;
        this.hitPoints = 5;
        this.roverBrokenState= OperationalStatus.OPERATIONAL;
    }

    public Rover(String roverId, RoverHeading roverHeading) {
        this(new RoverId(roverId), roverHeading);
    }


    public RoverId getRoverId() {
        return roverId;
    }

    private RoverHeading getRoverHeading() {
        return roverHeading;
    }

    public RoverState getRoverState(Location location) {
        return RoverState.newBuilder()
                .withRoverId(roverId)
                .withRoverHeading(roverHeading)
                .withCoordinate(location.coordinate())
                .withHitPoints(hitPoints)
                .withHealthState(roverBrokenState)
                .build();
    }

    public Location moveForward(Location currentLocation) {
        return switch (getRoverHeading()) {
            case NORTH -> currentLocation.incrementY();
            case EAST -> currentLocation.incrementX();
            case SOUTH -> currentLocation.decrementY();
            case WEST -> currentLocation.decrementX();
        };
    }


    public Location moveBackward(Location currentLocation) {
        return switch (getRoverHeading()) {
            case NORTH -> currentLocation.decrementY();
            case EAST -> currentLocation.decrementX();
            case SOUTH -> currentLocation.incrementY();
            case WEST -> currentLocation.incrementX();
        };
    }

    public void turnLeft() {
        roverHeading = roverHeading.nextCounterClockWise();
    }

    public void turnRight() {
        roverHeading = roverHeading.nextClockWise();
    }

    public void handleDamage() {
        if (roverIsAlive()) {
            subtractHitPoint();
            disableRoverIfNoHitpoints();
        }
    }

    public void breakRover() {
        hitPoints = 0;
        disableRoverIfNoHitpoints();
    }

    private boolean roverIsAlive() {
        return roverBrokenState == OperationalStatus.OPERATIONAL;
    }

    private void subtractHitPoint() {
        hitPoints--;
    }

    private void disableRoverIfNoHitpoints() {
        if (hitPoints == 0) {
            roverBrokenState = OperationalStatus.BROKEN;
        }
    }
}
