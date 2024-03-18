package io.tripled.marsrover.business.domain.rover;

import rover.RoverState;
import io.tripled.marsrover.vocabulary.Heading;
import io.tripled.marsrover.vocabulary.HealthState;
import io.tripled.marsrover.vocabulary.Location;
import io.tripled.marsrover.vocabulary.RoverId;

public class Rover {
    private final RoverId roverId;
    private Heading heading;
    private int hitPoints;
    private HealthState healthState;

    public Rover(RoverId roverId, Heading heading, int hitPoints, HealthState healthState) {
        this.roverId = roverId;
        this.heading = heading;
        this.hitPoints = hitPoints;
        this.healthState = healthState;
    }

    public Rover(String roverId, Heading heading) {
        this(new RoverId(roverId), heading, 5, HealthState.OPERATIONAL);
    }


    public RoverId getRoverId() {
        return roverId;
    }

    private Heading getRoverHeading() {
        return heading;
    }

    public RoverState getRoverState(Location location) {
        return RoverState.newBuilder()
                .withRoverId(roverId)
                .withRoverHeading(heading)
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
        heading = heading.nextCounterClockWise();
    }

    public void turnRight() {
        heading = heading.nextClockWise();
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
