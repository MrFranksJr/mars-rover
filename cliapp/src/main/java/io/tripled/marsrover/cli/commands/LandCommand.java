package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.Objects;

public class LandCommand implements Command {
    private final MarsRoverApi marsRoverApi;
    private Coordinate coordinate;
    private String simulationId;

    public LandCommand(String simulationId, Coordinate coordinate, MarsRoverApi marsRoverApi) {
        this.coordinate = coordinate;
        this.marsRoverApi = marsRoverApi;
        this.simulationId = simulationId;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.landRover(simulationId, coordinate, new LandingPresenterImpl(messagePresenter));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LandCommand that = (LandCommand) o;
        return Objects.equals(coordinate, that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}