package io.tripled.marsrover.events;

import io.tripled.marsrover.DTOs.RoverState;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.SimulationId;

public record LandingOnTopEvent(SimulationId id, RoverState landingRoverState, Coordinate coordinate) implements SimulationLandEvent {
}
