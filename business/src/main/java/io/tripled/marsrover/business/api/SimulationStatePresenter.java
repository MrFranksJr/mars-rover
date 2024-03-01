package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.simulation.Simulation;

import java.util.List;

public interface SimulationStatePresenter {
    void simulationState(List<Simulation> simulations);

    void simulationState(Simulation simulation);
}
