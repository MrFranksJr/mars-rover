package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.SimulationStatePresenter;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.List;
import java.util.Objects;

class StateCommand implements Command {
    private final MarsRoverApi marsRoverApi;
    private final String simulationId;

    public StateCommand(String simulationId, MarsRoverApi marsRoverApi) {
        this.marsRoverApi = marsRoverApi;
        this.simulationId = simulationId;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.lookUpSimulationState(simulationId, new SimulationStatePresenterImpl(messagePresenter));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateCommand that = (StateCommand) o;

        return Objects.equals(marsRoverApi, that.marsRoverApi);
    }

    @Override
    public int hashCode() {
        return marsRoverApi != null ? marsRoverApi.hashCode() : 0;
    }
}
