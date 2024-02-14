package io.tripled.marsrover.business.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.business.api.*;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.vocabulary.RoverMove;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.InstructionBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private final MarsRoverApi marsRoverApi;
    private final SimulationRepository simulationRepository;

    public StepDefinitions() {
        simulationRepository = new InMemSimulationRepo();
        marsRoverApi = new MarsRoverController(simulationRepository);
    }

    @Given("A simulation of size {int}")
    public void aSimulationOfSizeSimSize(int simulationSize) {
        marsRoverApi.initializeSimulation(simulationSize, LoggingSimulationCreationPresenter.INSTANCE);
    }

    @And("We land a rover on coordinates {int} {int}")
    public void weLandARoverOnCoordinatesXY(int x, int y) {
        final var coordinate = new Coordinate(x, y);
        marsRoverApi.landRover(coordinate, LoggingLandingPresenter.INSTANCE);
    }

    @When("We give the Rover {string} the Instruction {string} {int}")
    public void weGiveTheRoverTheInstructionAmount(String roverName, String instruction, int steps) {
        final InstructionBatch roverMoves = parseSingleRoverInstruction(roverName, instruction, steps);

        marsRoverApi.executeMoveInstructions(roverMoves,LogginRoverMovePresenter.INSTANCE);
    }

    @When("We give the Rover {string} the Instructions")
    public void weGiveTheRoverTheInstructions(String roverName, io.cucumber.datatable.DataTable dataTable) {

        final InstructionBatch instructionBatch = parseSingleRoverInstructionsDataTable(roverName, dataTable);

        marsRoverApi.executeMoveInstructions(instructionBatch, LogginRoverMovePresenter.INSTANCE);
    }

    @Then("The Rover {string} is at {int} {int} with orientation {string}")
    public void theRoverIsAtNewXNewYWithOrientation(String roverId, int newX, int newY, String heading) {
        if (simulationRepository.getSimulation().isPresent()) {
            final RoverState currentRoverState = getRoverState(roverId);
            assertEquals(roverId, currentRoverState.roverId().id());
            assertEquals(newX, currentRoverState.coordinate().xCoordinate());
            assertEquals(newY, currentRoverState.coordinate().yCoordinate());
            assertEquals(heading, currentRoverState.roverHeading().toString().toLowerCase());
        }
    }

    @Then("No rover should be present in the simulation")
    public void no_rover_should_be_present_in_the_simulation() {
        if (simulationRepository.getSimulation().isPresent()) {
            assertEquals(0, simulationRepository.getSimulation().get().takeSnapshot().roverList().size());
        }
    }

    @When("We give the Rovers the Instructions")
    public void weGiveTheRoversTheInstructions(io.cucumber.datatable.DataTable dataTable) {

        final InstructionBatch roverMoves = parseMultipleRoverInstructionsDataTable(dataTable);
        marsRoverApi.executeMoveInstructions(roverMoves, LogginRoverMovePresenter.INSTANCE);
    }

    @And("The timeline is")
    public void theTimeLineIs(io.cucumber.datatable.DataTable dataTable) {

    }

    private static InstructionBatch parseSingleRoverInstruction(String roverId, String direction, int amount) {
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        final RoverMove roverMove = new RoverMove(roverId, direction.substring(0, 1), amount);
        instructionBatch.addRoverMoves(roverId, roverMove);

        return instructionBatch.build();
    }

    private static InstructionBatch parseSingleRoverInstructionsDataTable(String roverId, DataTable dataTable) {
        final List<Map<String, String>> instructions = dataTable.asMaps(String.class, String.class);
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        for (Map<String, String> row : instructions) {
            final String command = row.get("instruction").substring(0, 1);
            final int amount = Integer.parseInt(row.get("amount"));
            final RoverMove roverMove = new RoverMove(roverId, command, amount);
            instructionBatch.addRoverMoves(roverId, roverMove);
        }
        return instructionBatch.build();
    }

    private static InstructionBatch parseMultipleRoverInstructionsDataTable(DataTable dataTable) {
        final List<Map<String, String>> instructions = dataTable.asMaps(String.class, String.class);
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        for (Map<String, String> row : instructions) {
            final String roverId = row.get("roverId");
            final String command = row.get("instruction").substring(0, 1);
            final int amount = Integer.parseInt(row.get("amount"));
            final RoverMove roverMove = new RoverMove(roverId, command, amount);
            instructionBatch.addRoverMoves(roverId, roverMove);
        }
        return instructionBatch.build();
    }

    private RoverState getRoverState(String roverName) {
        final Simulation simulation = simulationRepository.getSimulation().orElseThrow();
        return simulation.takeSnapshot().getRover(roverName).orElseThrow();
    }
}
