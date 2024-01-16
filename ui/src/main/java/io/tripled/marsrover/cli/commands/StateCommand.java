package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;

import java.util.Objects;

public class StateCommand implements Command {

    private final SimulationRepository simRepo;

    public StateCommand(SimulationRepository simRepo) {
        this.simRepo = simRepo;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        messagePresenter.stateCommand(simRepo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateCommand that = (StateCommand) o;

        return Objects.equals(simRepo, that.simRepo);
    }

    @Override
    public int hashCode() {
        return simRepo != null ? simRepo.hashCode() : 0;
    }
}
