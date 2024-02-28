package io.tripled.marsrover.vocabulary;

import java.util.UUID;

public record SimulationId(UUID id) {
    public static SimulationId create() {
        return new SimulationId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
