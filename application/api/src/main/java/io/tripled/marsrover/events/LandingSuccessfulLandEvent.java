package io.tripled.marsrover.events;

import rover.RoverState;
import io.tripled.marsrover.vocabulary.SimulationId;

public record LandingSuccessfulLandEvent(SimulationId id, RoverState roverState) implements SimulationLandEvent {
}
