package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.OperationalStatus;
import io.tripled.marsrover.business.domain.rover.RoverHeading;
import io.tripled.marsrover.vocabulary.RoverId;

public record RoverState(RoverId roverId, RoverHeading roverHeading, Coordinate coordinate, int hitpoints, OperationalStatus healthState) {


    private RoverState(Builder builder) {
        this(builder.roverId, builder.roverHeading, builder.coordinate, builder.hitPoints, builder.healthState);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private RoverId roverId;
        private RoverHeading roverHeading;
        private Coordinate coordinate;
        private int hitPoints;
        private OperationalStatus healthState;

        private Builder() {
        }

        public Builder withRoverId(RoverId val) {
            roverId = val;
            return this;
        }

        public Builder withRoverId(String val) {
            return withRoverId(new RoverId(val));
        }

        public Builder withRoverHeading(RoverHeading val) {
            roverHeading = val;
            return this;
        }

        public Builder withCoordinate(Coordinate val) {
            coordinate = val;
            return this;
        }

        public Builder withHitPoints(int val) {
            hitPoints = val;
            return this;
        }

        public Builder withHealthState(OperationalStatus val) {
            healthState = val;
            return this;
        }

        public RoverState build() {
            return new RoverState(this);
        }
    }
}
