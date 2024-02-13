package io.tripled.marsrover.business.api;

import io.tripled.marsrover.vocabulary.RoverId;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public record SimulationState(int simulationSize, int totalCoordinates, List<RoverState> roverList) {

    public static SimulationState NONE = new SimulationState(-1, -1, emptyList());

    private SimulationState(Builder builder) {
        this(builder.simulationSize, builder.totalCoordinates, unmodifiableList(builder.roverList));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<RoverState> getRover(String roverId) {
        return roverList.stream().filter(x -> x.roverId().id().equals(roverId)).findFirst();
    }

    public Optional<RoverState> getRover(RoverId roverId) {
        return getRover(roverId.id());
    }

    public static final class Builder {
        private int simulationSize;
        private int totalCoordinates;
        private List<RoverState> roverList;

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

        public Builder withRoverList(List<RoverState> rovers) {
            roverList = rovers;
            return this;
        }

        public SimulationState build() {
            return new SimulationState(this);
        }
    }
}