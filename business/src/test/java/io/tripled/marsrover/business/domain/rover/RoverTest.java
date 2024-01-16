package io.tripled.marsrover.business.domain.rover;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverTest {
    @Test
    void roverFacesNorthWhenLanded() {
        assertEquals("NORTH", new Rover("R1", 2, 3).getRoverHeading());
    }
}