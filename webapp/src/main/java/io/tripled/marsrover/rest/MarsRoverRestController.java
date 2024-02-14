package io.tripled.marsrover.rest;


import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MarsRoverRestController {

    private final MarsRoverApi marsRoverApi;

    public MarsRoverRestController(MarsRoverApi marsRoverApi) {
        this.marsRoverApi = marsRoverApi;
    }

    private static String buildRegularExpression(String[] inputArray) {
        return "^" + "( *[FfRrBbLl]\\d*)"
                .repeat(inputArray.length) +
                "$|^( *[FfRrBbLl]\\d*)$";
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
        if (xCoordinate <= simulationSize && yCoordinate <= simulationSize) {
            return "{\"result\":\"Landing successful\"}";
        } else {
            return "{\"result\":\"Landing unsuccessful\"}";
        }
    }

    @PostMapping("/api/moverover/{roverName}/{roverMoves}")
    @ResponseBody
    String moveRover(@PathVariable String roverName, @PathVariable String roverMoves) {
        final RoverMovePresenterImpl roverMovePresenter = new RoverMovePresenterImpl();
        final InstructionBatch roverInstructionsBatch = createInstructionBatchFromInput(roverName, roverMoves);

        marsRoverApi.executeMoveInstructions(roverInstructionsBatch, roverMovePresenter);

        if (!roverInstructionsBatch.batch().isEmpty()) {
            return "{\"result\":\"Rover moves successful\"}";
        } else {
            return "{\"result\":\"Rover moves unsuccessful\"}";
        }
    }

    private static InstructionBatch createInstructionBatchFromInput(String roverId, String roverMoves) {
        String[] inputArray = roverMoves.split(" ");
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        String regExpBuilder = buildRegularExpression(inputArray);

        Pattern regex = Pattern.compile(regExpBuilder);
        Matcher matcher = regex.matcher(roverMoves.trim());

        while (matcher.find()) {
            int groupCount = matcher.groupCount();
            for (int i = 1; i < groupCount; i++) {
                String preppedInput = matcher.group(i).trim();
                String direction = preppedInput.substring(0, 1);
                int steps = preppedInput.length() > 1 ? Integer.parseInt(preppedInput.substring(1)) : 1;
                final RoverMove roverMove = new RoverMove(direction, steps);
                instructionBatch.addRoverMoves(roverId, roverMove);
            }
        }
        return instructionBatch.build();
    }
}