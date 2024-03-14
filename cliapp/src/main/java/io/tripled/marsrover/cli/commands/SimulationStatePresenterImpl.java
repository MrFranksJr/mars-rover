package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DTOs.SimulationSnapshot;
import io.tripled.marsrover.SimulationStatePresenter;
import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.List;

public class SimulationStatePresenterImpl implements SimulationStatePresenter {
    private MessagePresenter messagePresenter;

    public SimulationStatePresenterImpl(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }

    @Override
    public void simulationState(List<SimulationSnapshot> simulationSnapshots) {
        for (SimulationSnapshot simulationSnapshot : simulationSnapshots) {
            messagePresenter.roverStateCommand(simulationSnapshot);
        }
    }

    @Override
    public void simulationState(SimulationSnapshot simulationSnapshot) {
        messagePresenter.roverStateCommand(simulationSnapshot);
    }
}
