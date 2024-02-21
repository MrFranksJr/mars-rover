package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.api.RoverState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverTest {

    private final Location location = new Location(new Coordinate(5, 5), 10);
    private Rover r1;

    @BeforeEach
    void setUp() {
        r1 = new Rover("R1", RoverHeading.NORTH);
    }
    @Test
    void moveOneStepForwardsPassLocation() {
        final var newLocation = r1.moveForward(location);

        RoverState roverState = r1.getRoverState(newLocation);

        assertEquals(6, roverState.coordinate().yCoordinate());
    }


    @Test
    void moveOneStepBackwardsPassLocation() {
        final Location newLocation = r1.moveBackward(location);

        RoverState roverState = r1.getRoverState(newLocation);

        assertEquals(4, roverState.coordinate().yCoordinate());
    }

    @Test
    void roverTurnsLeft() {
        r1.turnLeft();

        RoverState roverState = r1.getRoverState(new Location(new Coordinate(5,5), 10));

        assertEquals(RoverHeading.WEST, roverState.roverHeading());
    }

    @Test
    void roverTurnsRight() {
        r1.turnRight();

        RoverState roverState = r1.getRoverState(new Location(new Coordinate(5,5), 10));

        assertEquals(RoverHeading.EAST, roverState.roverHeading());
    }

    @Test
    void roverHasHitpoints() {
        RoverState roverState = r1.getRoverState(new Location(new Coordinate(5,5), 10));

        assertEquals(5, roverState.hitpoints());
    }

    @Test
    void roverCanLoseHitPoints() {
        r1.handleDamage();

        RoverState roverState = r1.getRoverState(new Location(new Coordinate(5,5), 10));

        assertEquals(4, roverState.hitpoints());
    }

    @Test
    void roverHitPointsCannotGoBelowZero() {
        r1.handleDamage();
        r1.handleDamage();
        r1.handleDamage();
        r1.handleDamage();
        r1.handleDamage();
        r1.handleDamage();

        RoverState roverState = r1.getRoverState(new Location(new Coordinate(5,5), 10));

        assertEquals(OperationalStatus.BROKEN, roverState.healthState());
        assertEquals(0, roverState.hitpoints());
    }
}