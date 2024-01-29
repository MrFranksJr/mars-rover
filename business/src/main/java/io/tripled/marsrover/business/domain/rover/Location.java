package io.tripled.marsrover.business.domain.rover;

public record Location(Coordinate coordinate, int simulationSize) {
    public Location {
        if (simulationSize < 0) {
            throw new IllegalArgumentException("Simulation size cannot be less than 0. Received Simulation size " + simulationSize);
        }
        if (coordinate.xCoordinate() > simulationSize || coordinate.yCoordinate() > simulationSize) {
            throw new IllegalArgumentException("Coordinate cannot be larger than the simulationsize. Received coordinate " + coordinate + " for Simulation " + simulationSize);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Location incrementY() {
        int y = calculateIncrementY();
        Coordinate c = coordinate.newY(y);
        return new Location(c, simulationSize);
    }

    private int calculateIncrementY() {
        return increment(coordinate().yCoordinate());
    }

    public Location incrementX() {
        int x = calculateIncrementX();
        final Coordinate c = coordinate.newX(x);
        return new Location(c, simulationSize);
    }

    public Location decrementX() {
        int x = calculateDecrementX();
        final Coordinate c = coordinate.newX(x);
        return new Location(c, simulationSize);
    }

    public Location decrementY() {
        int y = calculateDecrementY();
        Coordinate c = coordinate.newY(y);
        return new Location(c, simulationSize);
    }

    private int calculateDecrementY() {
        final int i = coordinate().yCoordinate();
        return decrement(i);
    }

    private int decrement(int i) {
        final int y = i - 1;

        if (y < 0)
            return simulationSize;
        else
            return y;
    }

    private int calculateIncrementX() {
        final int i = coordinate().xCoordinate();
        return increment(i);
    }

    private int increment(int i) {
        final int x = i + 1;

        if (x > simulationSize)
            return 0;
        else
            return x;
    }

    private int calculateDecrementX() {
        return decrement(coordinate().xCoordinate());
    }

    public static class Builder {
        private int xCoordinate;
        private int yCoordinate;
        private int simulationSize;

        public Builder setSimulationSize(int simulationSize) {
            this.simulationSize = simulationSize;
            return this;
        }

        public Builder withXCo(int xCoordinate) {
            this.xCoordinate = xCoordinate;
            return this;
        }

        public Builder withYco(int yCoordinate) {
            this.yCoordinate = yCoordinate;
            return this;
        }

        public Location build() {
            return new Location(new Coordinate(xCoordinate, yCoordinate), simulationSize);
        }
    }

}
