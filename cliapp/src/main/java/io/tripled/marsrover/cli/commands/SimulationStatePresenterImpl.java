package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.SimulationStatePresenter;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.List;

public class SimulationStatePresenterImpl implements SimulationStatePresenter {
    private MessagePresenter messagePresenter;

    public SimulationStatePresenterImpl(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }

    @Override
    public void simulationState(List<Simulation> simulations) {
        for (Simulation simulation : simulations) {
            messagePresenter.roverStateCommand(simulation.takeSnapshot());
        }
    }

    @Override
    public void simulationState(Simulation simulation) {
        messagePresenter.roverStateCommand(simulation.takeSnapshot());
    }
}
