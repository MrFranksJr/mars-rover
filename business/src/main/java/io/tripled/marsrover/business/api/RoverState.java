package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverHeading;
import io.tripled.marsrover.vocabulary.RoverId;

public record RoverState(RoverId roverId, RoverHeading roverHeading, Coordinate coordinate) {

    private RoverState(Builder builder) {
        this(builder.roverId, builder.roverHeading, builder.coordinate);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private RoverId roverId;
        private RoverHeading roverHeading;
        private Coordinate coordinate;
        private Builder() {
        }

        public Builder withRoverName(RoverId val) {
            roverId = val;
            return this;
        }
        public Builder withRoverName(String val) {
            return withRoverName(new RoverId(val));
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