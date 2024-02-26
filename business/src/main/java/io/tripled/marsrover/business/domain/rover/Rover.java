package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.RoverId;

public class Rover {
    private final RoverId roverId;
    private RoverHeading roverHeading;
    private int hitPoints;
    private HealthState healthState;

    public Rover(RoverId roverId, RoverHeading roverHeading, int hitPoints, HealthState healthState) {
        this.roverId = roverId;
        this.roverHeading = roverHeading;
        this.hitPoints = hitPoints;
        this.healthState = healthState;
    }

    public Rover(String roverId, RoverHeading roverHeading) {
        this(new RoverId(roverId), roverHeading, 5, HealthState.OPERATIONAL);
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
                .withHealthState(healthState)
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
        return healthState == HealthState.OPERATIONAL;
    }

    private void subtractHitPoint() {
        hitPoints--;
    }

    private void disableRoverIfNoHitpoints() {
        if (hitPoints == 0) {
            healthState = HealthState.BROKEN;
        }
    }
}
