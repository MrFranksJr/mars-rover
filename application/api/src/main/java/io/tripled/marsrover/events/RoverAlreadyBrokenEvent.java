package io.tripled.marsrover.events;

import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverAlreadyBrokenEvent(SimulationId id, RoverId roverId) implements SimulationMoveRoverEvent {
}
