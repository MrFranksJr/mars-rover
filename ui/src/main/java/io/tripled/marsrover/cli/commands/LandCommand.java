package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.Objects;

public class LandCommand implements Command {
    private final MarsRoverApi marsRoverApi;
    private Coordinate coordinate;

    public LandCommand(Coordinate coordinate, MarsRoverApi marsRoverApi) {
        this.coordinate = coordinate;
        this.marsRoverApi = marsRoverApi;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.landRover(coordinate, new LandingPresenter() {
            @Override
            public void landingSuccessful(RoverState state) {
                messagePresenter.landRoverMessage(state);
            }

            @Override
            public void roverMissesSimulation(int simulationSize) {
                messagePresenter.roverMissesSimulation(coordinate.xCoordinate(), coordinate.yCoordinate(), simulationSize);
            }

            @Override
            public void negativeCoordinatesReceived(Coordinate coordinate) {
                String coordinateString = coordinate.xCoordinate() + " " + coordinate.yCoordinate();
                messagePresenter.landingFailureCommand(coordinateString, LandingErrorTypes.NEGATIVE_INTS);
            }

            @Override
            public void simulationAlreadyPopulated(RoverState roverState) {
                messagePresenter.simulationAlreadyPopulated(roverState);
            }
        });
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