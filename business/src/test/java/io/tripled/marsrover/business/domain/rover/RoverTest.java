package io.tripled.marsrover.business.domain.rover;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverTest {

    private final Location location = new Location(new Coordinate(5, 5), 10);


    @Test
    void moveOneStepForwardsPassLocation() {
        Rover r1 = new Rover("R1", RoverHeading.NORTH);

        final var newLocation = r1.moveForward(location);

        assertEquals(6, newLocation.coordinate().yCoordinate());
    }


    @Test
    void moveOneStepBackwardsPassLocation() {
        Rover r1 = new Rover("R1", RoverHeading.NORTH);

        final Location newLocation = r1.moveBackward(location);

        assertEquals(4, newLocation.coordinate().yCoordinate());
    }

    @Test
    void roverTurnsLeft() {
        Rover r1 = new Rover("R1", RoverHeading.NORTH);

        r1.turnLeft();

        assertEquals(RoverHeading.WEST, r1.getRoverHeading());
    }

    @Test
    void roverTurnsRight() {
        Rover r1 = new Rover("R1", RoverHeading.NORTH);

        r1.turnRight();

        assertEquals(RoverHeading.EAST, r1.getRoverHeading());
    }

}