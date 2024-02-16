package io.tripled.marsrover.vocabulary;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        this(builder.getAllInstructions());
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<RoverInstructions> getInstructions(RoverId roverId) {
        return this.batch.stream().filter(x -> x.id() == roverId).findFirst();
    }

    public static final class Builder {
        private final Map<RoverId, RoverInstructions> instructionsMap = new HashMap<>();

        private Builder() {
        }

        public ImmutableList<RoverInstructions> getAllInstructions() {
            return ImmutableList.copyOf(instructionsMap.values());
        }

        public InstructionBatch build() {
            return new InstructionBatch(this);
        }

        public Builder addRoverMoves(RoverInstructions roverInstructions) {
            updateInstructions(roverInstructions);
            return this;
        }

        public Builder addRoverMoves(RoverId id, List<RoverMove> moves) {
            return this.addRoverMoves(new RoverInstructions(id, ImmutableList.copyOf(moves)));
        }

        public Builder addRoverMoves(String roverId, RoverMove roverMove) {
            return addRoverMoves(new RoverId(roverId), List.of(roverMove));
        }

        private void updateInstructions(RoverInstructions roverInstructions) {
            if (instructionsMap.containsKey(roverInstructions.id())) {
                final var existingInstructions = instructionsMap.get(roverInstructions.id());
                final var updatedInstructions = existingInstructions.addInstructions(roverInstructions);
                instructionsMap.put(roverInstructions.id(), updatedInstructions);
            } else {
                instructionsMap.put(roverInstructions.id(), roverInstructions);
            }
        }

        public void clearRoverMoves(String roverId) {
            instructionsMap.remove(new RoverId(roverId));
        }

        public int getInstructionSizeOfRover(String roverId) {
            RoverId key = new RoverId(roverId);
            if (instructionsMap.containsKey(key)) {
                return instructionsMap.get(key).moves().size();
            }
            return 0;
        }
    }
}
