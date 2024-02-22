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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (xCoordinate != that.xCoordinate) return false;
        return yCoordinate == that.yCoordinate;
    }

    @Override
    public int hashCode() {
        int result = xCoordinate;
        result = 31 * result + yCoordinate;
        return result;
    }
}