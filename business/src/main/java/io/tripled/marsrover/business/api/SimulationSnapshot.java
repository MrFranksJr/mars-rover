package io.tripled.marsrover.business.api;

import com.google.common.collect.ImmutableList;
import io.tripled.marsrover.vocabulary.RoverId;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public record SimulationSnapshot(int simulationSize, int totalCoordinates, List<RoverState> roverList) {

    public static SimulationSnapshot NONE = new SimulationSnapshot(-1, -1, emptyList());

    private SimulationSnapshot(Builder builder) {
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
            roverList = ImmutableList.sortedCopyOf((o1, o2) -> o1.roverId().compareWith(o2.roverId()), rovers);
            return this;
        }

        public SimulationSnapshot build() {
            return new SimulationSnapshot(this);
        }
    }
}