package io.tripled.marsrover;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.vocabulary.RoverId;

import java.util.List;

public class DummyPresenter implements MessagePresenter {
    public RoverState roverState;
    public SimulationState simulationState;
    public boolean hasRoverMissedSimulation = false;
    private boolean hasBeenCalled = false;
    private boolean hasRoverLanded = false;
    private boolean hasLandingFailed = false;
    private boolean hadStateCommandInvoked = false;
    private boolean hasRoverMoved;
    private boolean hasSimulationCreated = false;
    private boolean hasSimulationCreationFailed = false;

    public boolean hasRoverLanded() {
        return hasRoverLanded;
    }

    public boolean hasUnknownCommandBeenInvoked() {
        return hasBeenCalled;
    }

    public boolean hasRoverMissedSimulationBeenInvoked() {
        return hasRoverMissedSimulation;
    }

    public boolean invalidLandingInstruction() {
        return hasLandingFailed;
    }

    public boolean hasRoverMoved() {
        return hasRoverMoved;
    }

    public boolean hasSimulationBeenCreated() {
        return hasSimulationCreated;
    }

    public boolean hasSimulationCreationFailed() {
        return hasSimulationCreationFailed;
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
    public void simulationCreationSuccessful(SimulationState simulationState) {
        hasSimulationCreated = true;
        this.simulationState = simulationState;
    }

    @Override
    public void simulationCreationUnsuccessful(String simulationSize) {
        hasSimulationCreationFailed = true;
    }

    @Override
    public void landRoverMessage(RoverState roverState) {
        hasRoverLanded = true;
        this.roverState = roverState;
    }

    @Override
    public void landingFailureCommand(String coordinateString, LandingErrorTypes landingError) {
        hasLandingFailed = true;
    }

    @Override
    public void roverStateCommand(SimulationState simulationState) {
        setRoverState(simulationState);
        this.hadStateCommandInvoked = true;
    }

    private void setRoverState(SimulationState simulationState) {
        final List<RoverState> rovers = simulationState.roverList();
        if (rovers.isEmpty())
            this.roverState = null;
        else
            this.roverState = rovers.getFirst();
    }

    public boolean hasStateCommandBeenInvoked() {
        return hadStateCommandInvoked;
    }

    @Override
    public void roverMissesSimulation(int xCoordinate, int yCoordinate, int simulationSize) {
        hasRoverMissedSimulation = true;
    }

    @Override
    public void roverMovedMessage(RoverState roverState) {
        this.roverState = roverState;
        hasRoverMoved = true;
    }

    @Override
    public void roverDoesNotExist() {

    }

    @Override
    public void duplicateSimulationDetected(SimulationState simulationState) {
        hasSimulationCreated = false;
        this.simulationState = simulationState;
    }

    @Override
    public void roverCollidedMessage(RoverId roverId) {

    }

}