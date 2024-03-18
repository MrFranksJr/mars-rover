package io.tripled.marsrover.events;

public interface SimulationRoverMovedEventPublisher {
    void publish(SimulationMoveRoverEvent event);
}
