package io.tripled.marsrover.api.rover;

import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.HealthState;
import io.tripled.marsrover.vocabulary.Heading;
import io.tripled.marsrover.vocabulary.RoverId;

public record RoverState(RoverId roverId, Heading heading, Coordinate coordinate, int hitpoints, HealthState healthState) {


    private RoverState(Builder builder) {
        this(builder.roverId, builder.heading, builder.coordinate, builder.hitPoints, builder.healthState);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private RoverId roverId;
        private Heading heading;
        private Coordinate coordinate;
        private int hitPoints;
        private HealthState healthState;

        private Builder() {
        }

        public Builder withRoverId(RoverId val) {
            roverId = val;
            return this;
        }

        public Builder withRoverId(String val) {
            return withRoverId(new RoverId(val));
        }

        public Builder withRoverHeading(Heading val) {
            heading = val;
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

        public Builder withHealthState(HealthState val) {
            healthState = val;
            return this;
        }

        public RoverState build() {
            return new RoverState(this);
        }
    }
}
