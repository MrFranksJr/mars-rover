package io.tripled.marsrover.events;

import io.tripled.marsrover.DTOs.RoverState;
import io.tripled.marsrover.vocabulary.SimulationId;

public record LandingSuccessfulLandEvent(SimulationId id, RoverState roverState) implements SimulationLandEvent {
}
