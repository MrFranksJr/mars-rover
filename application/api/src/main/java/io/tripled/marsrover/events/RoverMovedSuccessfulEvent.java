package io.tripled.marsrover.events;

import rover.RoverState;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverMovedSuccessfulEvent(SimulationId id, RoverState roverState) implements SimulationMoveRoverEvent {
}
