package io.tripled.marsrover;

import rover.RoverState;
import io.tripled.marsrover.simulation.SimulationSnapshot;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.events.LandingOnTopEvent;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;
import java.util.UUID;

public class DummyPresenter implements MessagePresenter {
    public RoverState roverState;
    public SimulationSnapshot simulationSnapshot;
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
    public void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot) {
        hasSimulationCreated = true;
        this.simulationSnapshot = simulationSnapshot;
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
    public void roverStateCommand(SimulationSnapshot simulationSnapshot) {
        setRoverState(simulationSnapshot);
        this.hadStateCommandInvoked = true;
    }

    private void setRoverState(SimulationSnapshot simulationSnapshot) {
        final List<RoverState> rovers = simulationSnapshot.roverList();
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
    public void roverCollidedMessage(RoverState roverState) {
    }

    @Override
    public void roverBrokenMessage(RoverState roverState) {
    }

    @Override
    public void roverAlreadyBrokenMessage(RoverId roverId) {
    }

    @Override
    public void landRoversOnTopMessage(LandingOnTopEvent landingOnTopEvent) {

    }

    @Override
    public void roverMoveErrorMessage() {

    }

    @Override
    public SimulationId getSimId() {
        if (simulationSnapshot == null) {
            return new SimulationId(UUID.randomUUID());
        }
        return simulationSnapshot.id();
    }
}