package io.tripled.marsrover;

import io.tripled.marsrover.DTOs.RoverState;
import io.tripled.marsrover.DTOs.SimulationSnapshot;
import io.tripled.marsrover.events.LandingSuccessfulLandEvent;
import io.tripled.marsrover.events.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.events.RoverMovedSuccessfulEvent;
import io.tripled.marsrover.events.SimulationLandEventPublisher;
import io.tripled.marsrover.vocabulary.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class MarsRoverControllerImpl implements MarsRoverApi {
    private final SimulationRepository simulationRepository;
    public MarsRoverControllerImpl(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    @Override
    public void lookUpSimulationState(String simulationId, SimulationStatePresenter simulationStatePresenter) {
        final SimulationSnapshot simulationSnapshot = SimulationSnapshot.newBuilder().withId(new SimulationId(UUID.randomUUID())).withSimSize(10).withRoverList(new ArrayList<>()).build();
        simulationStatePresenter.simulationState(simulationSnapshot);
    }

    @Override
    public void lookUpSimulationStates(SimulationStatePresenter simulationStatePresenter) {

    }

    @Override
    public void landRover(String simulationId, Coordinate coordinate, LandingPresenter landingPresenter) {
        SimulationId simId = new SimulationId(UUID.fromString(simulationId));
        Optional<SimulationSnapshot> simulationSnapshot = simulationRepository.getSimulationSnapshot(simId);
        final int simulationSize = simulationSnapshot.orElseThrow().simulationSize();
        if (simulationSize < coordinate.yCoordinate() || simulationSize < coordinate.xCoordinate()) {
            landingPresenter.roverMissesSimulation(new RoverMissesSimulationLandEvent(simId, simulationSnapshot.get().simulationSize(), coordinate));
        }
        else {
            landingPresenter.landingSuccessful(new LandingSuccessfulLandEvent(simId, new RoverState(new RoverId("R1"), Heading.NORTH, coordinate, 5, HealthState.OPERATIONAL)));
        }
    }

    @Override
    public void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter) {
        final SimulationSnapshot simulationSnapshot = SimulationSnapshot.newBuilder().withId(new SimulationId(UUID.randomUUID())).withSimSize(simulationSize).withRoverList(new ArrayList<>()).build();
        simulationRepository.add(simulationSnapshot);
        simulationCreationPresenter.simulationCreationSuccessful(simulationSnapshot);
    }

    @Override
    public void executeMoveInstructions(String simulationId, InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter) {
        roverMovePresenter.moveRoverSuccessful(new RoverMovedSuccessfulEvent(new SimulationId(UUID.fromString(simulationId)), new RoverState(new RoverId("R1"), Heading.NORTH, new Coordinate(0,0), 5, HealthState.OPERATIONAL)));
    }
}
