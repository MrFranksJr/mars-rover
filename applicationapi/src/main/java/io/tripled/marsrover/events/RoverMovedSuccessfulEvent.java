package io.tripled.marsrover.events;

import io.tripled.marsrover.DTOs.RoverState;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverMovedSuccessfulEvent(SimulationId id, RoverState roverState) implements SimulationMoveRoverEvent {
}
