package io.tripled.marsrover.vocabulary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void xCoordinateCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordinate(-1,20);
        });
    }

    @Test
    void yCoordinateCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordinate(5,-20);
        });
    }
}