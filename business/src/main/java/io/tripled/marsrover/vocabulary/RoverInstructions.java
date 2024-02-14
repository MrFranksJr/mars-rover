package io.tripled.marsrover.vocabulary;

import com.google.common.collect.ImmutableList;

public record RoverInstructions(RoverId id, ImmutableList<RoverMove> moves) {

    public RoverInstructions addInstructions(RoverInstructions toAdd) {
        return new RoverInstructions(id, ImmutableList.<RoverMove>builder()
                .addAll(moves)
                .addAll(toAdd.moves)
                .build());
    }
}
