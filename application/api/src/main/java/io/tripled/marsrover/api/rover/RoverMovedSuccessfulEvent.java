package io.tripled.marsrover.api.rover;

import io.tripled.marsrover.api.simulation.SimulationMoveRoverEvent;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverMovedSuccessfulEvent(SimulationId id, RoverState roverState) implements SimulationMoveRoverEvent {
}
