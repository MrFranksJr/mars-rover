package io.tripled.marsrover.business.domain.rover;

public record Coordinate(int xCoordinate, int yCoordinate) {
    public Coordinate {
        if (coordinateIsNegative(xCoordinate) || coordinateIsNegative(yCoordinate)) {
            throw new IllegalArgumentException("Coordinates must be larger than 0!");
        }
    }

    private boolean coordinateIsNegative(int coordinate) {
        return coordinate < 0;
    }

    public Coordinate newY(int y) {
        return new Coordinate(xCoordinate, y);
    }

    public Coordinate newX(int x) {
        return new Coordinate(x, yCoordinate);
    }

    @Override
    public String toString() {
        return "[" + xCoordinate +
               " - " + yCoordinate +
               ']';
    }
}