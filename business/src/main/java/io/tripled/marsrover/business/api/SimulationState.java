package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Rover;

import java.util.List;

public record SimulationState(int simulationSize, int totalCoordinates, List<Rover> roverList) {

    private SimulationState(Builder builder) {
        this(builder.simulationSize, builder.totalCoordinates, builder.roverList);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private int simulationSize;
        private int totalCoordinates;
        private List<Rover> roverList;
        private Builder() {
        }

        public Builder withSimSize(int value) {
            simulationSize = value;
            return this;
        }

        public Builder withTotalCoordinates(int value) {
            totalCoordinates = value;
            return this;
        }

        public Builder withRoverList(List<Rover> rovers) {
            roverList = rovers;
            return this;
        }

        public SimulationState build() {
            return new SimulationState(this);
        }
    }
}