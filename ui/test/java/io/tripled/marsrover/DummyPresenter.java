package io.tripled.marsrover;

import io.tripled.marsrover.commands.LandingErrorTypes;
import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsroverbusiness.simulation.SimulationRepository;
import io.tripled.marsroverbusiness.rover.Rover;

public class DummyPresenter implements MessagePresenter {
    private boolean hasBeenCalled = false;
    private boolean hasRoverLanded = false;
    public int roverLandinsPosX;
    public int roverLandinsPosY;
    public Rover rover;
    public boolean hasRoverMissedSimulation = false;
    private boolean hasLandingFailed = false;

    public boolean hasLandingCommandBeenInvoked() { return hasRoverLanded; };

    public boolean hasUnknownCommandBeenInvoked() {
        return hasBeenCalled;
    }
    public boolean hasRoverMissedSimulationBeenInvoked() {
        return hasRoverMissedSimulation;
    }
    public boolean hasLandingFailedCommandBeenInvoked() {
        return hasLandingFailed;
    }

    @Override
    public void welcomeMessage() {

    }

    @Override
    public void unknownCommand(String input) {
        hasBeenCalled = true;
    }

    @Override
    public void printCommand() {
    }

    @Override
    public void quitMessage() {

    }

    @Override
    public void simSetupMessage(int maxCoordinate, int simSize) {

    }

    @Override
    public void invalidSimSetupMessage(String maxCoordinate) {

    }

    @Override
    public void landRoverMessage(int landingPosX, int landingPosY, Rover rover) {
        hasRoverLanded = true;
        this.rover = rover;
        this.roverLandinsPosX = landingPosX;
        this.roverLandinsPosY = landingPosY;
    }
    @Override
    public void landingFailureCommand(String coordinateString, LandingErrorTypes landingError) {
        hasLandingFailed = true;
    }

    @Override
    public void stateCommand(SimulationRepository simRepo) {

    }

    @Override
    public void roverMissesSimulation(int xCoordinate, int yCoordinate, SimulationRepository simRepo) {
        hasRoverMissedSimulation = true;
    }
}