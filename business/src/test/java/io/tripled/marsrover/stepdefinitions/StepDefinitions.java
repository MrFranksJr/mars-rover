package io.tripled.marsrover.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.business.api.MarsRoverController;
import rover.RoverState;
import io.tripled.marsrover.simulation.SimulationSnapshot;
import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StepDefinitions {
    private final SimulationRepository simulationRepository;
    private final MarsRoverApi marsRoverApi;
    private final SimulationQuery simulationQuery;
    private final UUID testUUID;

    public StepDefinitions() {
        simulationRepository = new DummySimulationRepository();
        marsRoverApi = new MarsRoverController(simulationRepository);
        simulationQuery = (SimulationQuery) simulationRepository;

        testUUID = UUID.randomUUID();
    }

    private static InstructionBatch parseSingleRoverInstruction(String roverId, String direction, int amount) {
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        final RoverMove roverMove = new RoverMove(direction.substring(0, 1), amount);
        instructionBatch.addRoverMoves(roverId, roverMove);

        return instructionBatch.build();
    }

    private static InstructionBatch parseSingleRoverInstructionsDataTable(String roverId, DataTable dataTable) {
        final List<Map<String, String>> instructions = dataTable.asMaps(String.class, String.class);
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        for (Map<String, String> row : instructions) {
            final String command = row.get("instruction").substring(0, 1);
            final int amount = Integer.parseInt(row.get("amount"));
            final RoverMove roverMove = new RoverMove(command, amount);
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
            final RoverMove roverMove = new RoverMove(command, amount);
            instructionBatch.addRoverMoves(roverId, roverMove);
        }
        return instructionBatch.build();
    }

    @Given("A simulation of size {int}")
    public void aSimulationOfSizeSimSize(int simulationSize) {
        marsRoverApi.initializeSimulation(simulationSize, LoggingSimulationCreationPresenter.INSTANCE);
    }

    @And("We land a rover on coordinates {int} {int}")
    public void weLandARoverOnCoordinatesXY(int x, int y) {
        final var coordinate = new Coordinate(x, y);
        marsRoverApi.landRover(testUUID.toString(), coordinate, LoggingLandingPresenter.INSTANCE);
    }

    @When("We give the Rover {string} the Instruction {string} {int}")
    public void weGiveTheRoverTheInstructionAmount(String roverName, String instruction, int steps) {
        final InstructionBatch roverMoves = parseSingleRoverInstruction(roverName, instruction, steps);

        marsRoverApi.executeMoveInstructions(testUUID.toString(), roverMoves, LogginRoverMovePresenter.INSTANCE);
    }

    @When("We give the Rover {string} the Instructions")
    public void weGiveTheRoverTheInstructions(String roverName, io.cucumber.datatable.DataTable dataTable) {

        final InstructionBatch instructionBatch = parseSingleRoverInstructionsDataTable(roverName, dataTable);

        marsRoverApi.executeMoveInstructions(testUUID.toString(), instructionBatch, LogginRoverMovePresenter.INSTANCE);
    }

    @Then("The Rover {string} is at {int} {int} with orientation {string}")
    public void theRoverIsAtNewXNewYWithOrientation(String roverId, int newX, int newY, String heading) {
        final RoverState currentRoverState = getRoverState(roverId);
        Assertions.assertEquals(roverId, currentRoverState.roverId().id());
        Assertions.assertEquals(newX, currentRoverState.coordinate().xCoordinate());
        Assertions.assertEquals(newY, currentRoverState.coordinate().yCoordinate());
        Assertions.assertEquals(heading, currentRoverState.heading().toString().toLowerCase());
    }

    @Then("No rover should be present in the simulation")
    public void no_rover_should_be_present_in_the_simulation() {
        final SimulationSnapshot snapshot = simulationQuery.getSimulationInformation();
        Assertions.assertEquals(0, snapshot.roverList().size());
    }

    @When("We give the Rovers the Instructions")
    public void weGiveTheRoversTheInstructions(io.cucumber.datatable.DataTable dataTable) {

        final InstructionBatch roverMoves = parseMultipleRoverInstructionsDataTable(dataTable);
        marsRoverApi.executeMoveInstructions(testUUID.toString(), roverMoves, LogginRoverMovePresenter.INSTANCE);
    }

    @And("The timeline is")
    public void theTimeLineIs(io.cucumber.datatable.DataTable dataTable) {

    }

    private RoverState getRoverState(String roverId) {
        final SimulationSnapshot snapshot = simulationQuery.getSimulationInformation();
        return snapshot.getRover(roverId).orElseThrow();
    }
}
