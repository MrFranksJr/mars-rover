package io.tripled.marsrover.vocabulary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LocationTest {

    @Test
    void coordinatesCannotBeBiggerThanSimulationSize() {
        final Coordinate coordinate = new Coordinate(20, 20);
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(coordinate, 10);
        });
    }

    @Test
    void simulationSizeCannotBeSmallerThanZero() {
        final Coordinate coordinate = new Coordinate(5, 20);
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(coordinate, -10);
        });
    }
}