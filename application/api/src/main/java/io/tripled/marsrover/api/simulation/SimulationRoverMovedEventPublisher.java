package io.tripled.marsrover.api.simulation;

public interface SimulationRoverMovedEventPublisher {
    void publish(SimulationMoveRoverEvent event);
}
