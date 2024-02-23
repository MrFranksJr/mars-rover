package io.tripled.marsrover.rest;


import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import org.springframework.web.bind.annotation.*;

import static io.tripled.marsrover.rest.InputParser.INPUT_PARSER;

@RestController
public class MarsRoverRestController {

    private final MarsRoverApi marsRoverApi;

    public MarsRoverRestController(MarsRoverApi marsRoverApi) {
        this.marsRoverApi = marsRoverApi;
    }

    @PostMapping("/api/createsimulation/{simulationSize}")
    void createSimulation(@PathVariable int simulationSize) {
        SimulationCreationPresenterImpl simulationCreationPresenter = new SimulationCreationPresenterImpl();
        marsRoverApi.initializeSimulation(simulationSize, simulationCreationPresenter);
    }

    @GetMapping("/api/simulationstate")
    SimulationViewDTO getSimulationState() {
        SimulationStateRestPresenter simulationStatePresenter = new SimulationStateRestPresenter();

        marsRoverApi.lookUpSimulationState(simulationStatePresenter);

        return simulationStatePresenter.getSimulationState();

    }

    @PostMapping("/api/landrover/{xCoordinate}/{yCoordinate}")
    @ResponseBody
    String landRover(@PathVariable int xCoordinate, @PathVariable int yCoordinate) {
        Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);

        LandingPresenterImpl landingPresenter = new LandingPresenterImpl();
        marsRoverApi.landRover(coordinate, landingPresenter);
        SimulationStateRestPresenter simulationStatePresenter = new SimulationStateRestPresenter();

        marsRoverApi.lookUpSimulationState(simulationStatePresenter);
        int simulationSize = simulationStatePresenter.getSimulationState().simulationSize();

        if(landingPresenter.isLandingOnTopEvent()){
            return "{\"result\":\"Landing on top\"}";
        } else if (xCoordinate <= simulationSize && yCoordinate <= simulationSize) {
            return "{\"result\":\"Landing successful\"}";
        } else {
            return "{\"result\":\"Landing unsuccessful\"}";
        }
    }

    @PostMapping("/api/moverover/{roverInstructions}")
    @ResponseBody
    String moveRover(@PathVariable String roverInstructions) {
        final RoverMovePresenterImpl roverMovePresenter = new RoverMovePresenterImpl();
        final InstructionBatch roverInstructionsBatch = INPUT_PARSER.extractRoverMovesFromInput(roverInstructions);

        marsRoverApi.executeMoveInstructions(roverInstructionsBatch, roverMovePresenter);

        if (!roverInstructionsBatch.batch().isEmpty() && roverMovePresenter.isAlreadyBroken().second()) {
            String roverId = roverMovePresenter.isAlreadyBroken().first().id();
            return "{\"result\":\"Rover " + roverId + " is already down and cannot move\"}";
        } else if (!roverInstructionsBatch.batch().isEmpty() && roverMovePresenter.isBroken().second()) {
            String roverId = roverMovePresenter.isBroken().first().id();
            return "{\"result\":\"Rover " + roverId + " has broken down\"}";
        } else if (!roverInstructionsBatch.batch().isEmpty() && !roverMovePresenter.hasCollided().second()) {
            return "{\"result\":\"Rover moves successfully\"}";
        } else if(!roverInstructionsBatch.batch().isEmpty() && roverMovePresenter.hasCollided().second()) {
            String roverId = roverMovePresenter.hasCollided().first().id();
            return "{\"result\":\"Rover " + roverId + " has collided\"}";
        } else {
            return "{\"result\":\"Cannot execute instructions\"}";
        }
    }
}