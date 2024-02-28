package io.tripled.marsrover.business.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import io.tripled.marsrover.business.domain.rover.Location;
import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.vocabulary.RoverId;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public record SimulationSnapshot(ObjectId id, int simulationSize, int totalCoordinates, List<RoverState> roverList) {

    public static SimulationSnapshot NONE = new SimulationSnapshot(null,-1, -1, emptyList());

    private SimulationSnapshot(Builder builder) {
        this(builder._id, builder.simulationSize, builder.totalCoordinates, unmodifiableList(builder.roverList));
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
        private UUID uuId;
        private ObjectId _id;
        private int simulationSize;
        private int totalCoordinates;
        private List<RoverState> roverList;

        private Builder() {
        }

        public Builder withUUID(UUID value) {
            uuId = value;
            return this;
        }

        public Builder withId(ObjectId value) {
            _id = value;
            return this;
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