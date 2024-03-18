package io.tripled.marsrover;

import io.tripled.marsrover.simulation.SimulationSnapshot;

public interface SimulationCreationPresenter {
    void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot);

    void simulationCreationUnsuccessful(int simulationSize);

}
