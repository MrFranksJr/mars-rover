package io.tripled.marsrover.vocabulary;

import com.google.common.collect.ImmutableList;
import io.tripled.marsrover.business.domain.rover.RoverMove;

import java.util.List;

public record InstructionBatch(ImmutableList<RoverInstructions> batch) {

    public InstructionBatch {
        final var nrOfDistinctRoverIds = batch.stream()
                .map(RoverInstructions::id)
                .distinct()
                .count();
        if (nrOfDistinctRoverIds != batch.size()) {
            throw new IllegalArgumentException("There are double roverIds in the instructions batch " + batch);
        }
    }

    public InstructionBatch(Builder builder) {
        this(builder.builder.build());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private ImmutableList.Builder<RoverInstructions> builder = ImmutableList.builder();

        private Builder() {
        }


        public InstructionBatch build() {
            return new InstructionBatch(this);
        }

        public Builder addRoverMoves(RoverInstructions roverInstructions) {
            builder.add(roverInstructions);
            return this;
        }

        public Builder addRoverMoves(RoverId id, List<RoverMove> moves) {
            return this.addRoverMoves(new RoverInstructions(id, ImmutableList.copyOf(moves)));
        }

        public Builder addRoverMoves(String roverId, RoverMove roverMove) {
            return addRoverMoves(new RoverId(roverId), List.of(roverMove));
        }
    }
}
