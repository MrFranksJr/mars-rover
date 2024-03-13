package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.tripled.marsrover.vocabulary.Heading;
import io.tripled.marsrover.vocabulary.HealthState;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverTest {

    private final Location location = new Location(new Coordinate(5, 5), 10);
    private Rover r1;

    @BeforeEach
    void setUp() {
        r1 = new Rover("R1", Heading.NORTH);
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

        assertEquals(Heading.WEST, roverState.heading());
    }

    @Test
    void roverTurnsRight() {
        r1.turnRight();

        RoverState roverState = r1.getRoverState(new Location(new Coordinate(5,5), 10));

        assertEquals(Heading.EAST, roverState.heading());
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

        assertEquals(HealthState.BROKEN, roverState.healthState());
        assertEquals(0, roverState.hitpoints());
    }
}