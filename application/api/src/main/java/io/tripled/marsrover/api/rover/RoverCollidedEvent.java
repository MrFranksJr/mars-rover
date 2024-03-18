package io.tripled.marsrover.api.rover;

import io.tripled.marsrover.api.simulation.SimulationMoveRoverEvent;
import io.tripled.marsrover.vocabulary.Location;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverCollidedEvent(SimulationId id, RoverState roverState, Location newLocation) implements SimulationMoveRoverEvent {
}