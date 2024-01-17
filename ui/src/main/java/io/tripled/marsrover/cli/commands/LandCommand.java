package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.messages.MessagePresenter;

public class LandCommand implements Command {
    private final int xCoordinate;
    private final int yCoordinate;
    private final MarsRoverApi marsRoverApi;

    public LandCommand(int xCoordinate, int yCoordinate, MarsRoverApi marsRoverApi) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.marsRoverApi = marsRoverApi;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.landRover(xCoordinate, yCoordinate, new LandingPresenter() {
            @Override
            public void landingSuccessful(RoverState state) {
                messagePresenter.landRoverMessage(state);
            }

            @Override
            public void roverMissesSimulation(int simulationSize) {
                messagePresenter.roverMissesSimulation(xCoordinate, yCoordinate, simulationSize);
            }

            @Override
            public void negativeCoordinatesReceived(int x, int y) {
                String coordinateString = xCoordinate + " " + yCoordinate;
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

        if (xCoordinate != that.xCoordinate) return false;
        return yCoordinate == that.yCoordinate;
    }

    @Override
    public int hashCode() {
        int result = xCoordinate;
        result = 31 * result + yCoordinate;
        return result;
    }
}