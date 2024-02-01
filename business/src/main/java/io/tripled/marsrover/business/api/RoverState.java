package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverHeading;

public record RoverState(String roverName, RoverHeading roverHeading, Coordinate coordinate) {

    private RoverState(Builder builder) {
        this(builder.roverName, builder.roverHeading, builder.coordinate);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String roverName;
        private RoverHeading roverHeading;
        private Coordinate coordinate;
        private Builder() {
        }

        public Builder withRoverName(String val) {
            roverName = val;
            return this;
        }

        public Builder withRoverHeading(RoverHeading val) {
            roverHeading = val;
            return this;
        }

        public Builder withCoordinate(Coordinate val) {
            coordinate = val;
            return this;
        }

        public RoverState build() {
            return new RoverState(this);
        }
    }
}