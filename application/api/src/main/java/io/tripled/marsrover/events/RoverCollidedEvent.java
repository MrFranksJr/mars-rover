package io.tripled.marsrover.events;

import rover.RoverState;
import io.tripled.marsrover.vocabulary.Location;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverCollidedEvent(SimulationId id, RoverState roverState, Location newLocation) implements SimulationMoveRoverEvent {
}