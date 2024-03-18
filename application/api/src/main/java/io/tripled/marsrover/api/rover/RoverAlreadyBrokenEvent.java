package io.tripled.marsrover.api.rover;

import io.tripled.marsrover.api.simulation.SimulationMoveRoverEvent;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverAlreadyBrokenEvent(SimulationId id, RoverId roverId) implements SimulationMoveRoverEvent {
}
