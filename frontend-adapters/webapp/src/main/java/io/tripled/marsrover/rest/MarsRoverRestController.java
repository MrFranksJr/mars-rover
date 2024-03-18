package io.tripled.marsrover.rest;

import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.dto.RoverLandingResultDTO;
import io.tripled.marsrover.dto.RoverMoveResultDTO;
import io.tripled.marsrover.dto.SimulationCreationDTO;
import io.tripled.marsrover.dto.SimulationViewDTO;
import io.tripled.marsrover.presenters.LandingPresenterImpl;
import io.tripled.marsrover.presenters.RoverMovePresenterImpl;
import io.tripled.marsrover.presenters.SimulationCreationPresenterImpl;
import io.tripled.marsrover.presenters.SimulationStateRestPresenter;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.tripled.marsrover.rest.InputParser.INPUT_PARSER;
import static io.tripled.marsrover.rest.ResultParser.RESULT_PARSER;

@RestController
public class MarsRoverRestController {

    private final MarsRoverApi marsRoverApi;

    public MarsRoverRestController(MarsRoverApi marsRoverApi) {
        this.marsRoverApi = marsRoverApi;
    }

    @PostMapping("/api/createsimulation/{simulationSize}")
    SimulationCreationDTO createSimulation(@PathVariable int simulationSize) {
        SimulationCreationPresenterImpl simulationCreationPresenter = new SimulationCreationPresenterImpl();
        marsRoverApi.initializeSimulation(simulationSize, simulationCreationPresenter);

        return RESULT_PARSER.simulationCreationResult(simulationCreationPresenter);
    }

    @GetMapping("/api/simulationstate/{simulationId}")
    SimulationViewDTO getSimulationState(@PathVariable String simulationId) {
        SimulationStateRestPresenter simulationStatePresenter = new SimulationStateRestPresenter();

        marsRoverApi.lookUpSimulationState(simulationId, simulationStatePresenter);

        return simulationStatePresenter.getSimulationState();
    }
    @GetMapping("/api/simulationstates")
    List<SimulationViewDTO> getSimulationStates() {
        SimulationStateRestPresenter simulationStatePresenter = new SimulationStateRestPresenter();

        marsRoverApi.lookUpSimulationStates(simulationStatePresenter);

        return simulationStatePresenter.getSimulationStates();
    }

    @PostMapping("/api/landrover/{simulationId}/{xCoordinate}/{yCoordinate}")
    @ResponseBody
    RoverLandingResultDTO landRover(@PathVariable String simulationId, @PathVariable int xCoordinate, @PathVariable int yCoordinate) {
        Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);
        LandingPresenterImpl landingPresenter = new LandingPresenterImpl();

        marsRoverApi.landRover(simulationId, coordinate, landingPresenter);

        return RESULT_PARSER.landExecutionResult(landingPresenter);
    }

    @PostMapping("/api/moverover/{simulationId}/{roverInstructions}")
    @ResponseBody
    RoverMoveResultDTO moveRover(@PathVariable String simulationId, @PathVariable String roverInstructions) {
        final RoverMovePresenterImpl roverMovePresenter = new RoverMovePresenterImpl();
        final InstructionBatch roverInstructionsBatch = INPUT_PARSER.extractRoverMovesFromInput(roverInstructions);

        marsRoverApi.executeMoveInstructions(simulationId, roverInstructionsBatch, roverMovePresenter);

        return RESULT_PARSER.moveExecutionResult(roverInstructionsBatch, roverMovePresenter);

    }
}