package io.tripled.marsrover.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.api.rover.RoverState;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class StepDefinitions {
    private final SimulationRepository simulationRepository;
    private final MarsRoverApi marsRoverApi;
    private final SimulationQuery simulationQuery;
    private SimulationId simulationId;
    private final HashMap<Integer, SimulationId> simulationMap = new HashMap<>();

    public StepDefinitions() {
        simulationRepository = new DummySimulationRepository();
        marsRoverApi = new MarsRoverController(simulationRepository);
        simulationQuery = (SimulationQuery) simulationRepository;
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
        simulationId = simulationRepository.getSimulationSnapshots().orElseThrow().getFirst().id();
    }

    @And("We land a rover on coordinates {int} {int}")
    public void weLandARoverOnCoordinatesXY(int x, int y) {
        final var coordinate = new Coordinate(x, y);
        marsRoverApi.landRover(simulationId.toString(), coordinate, LoggingLandingPresenter.INSTANCE);
    }

    @When("We give the Rover {string} the Instruction {string} {int}")
    public void weGiveTheRoverTheInstructionAmount(String roverName, String instruction, int steps) {
        final InstructionBatch roverMoves = parseSingleRoverInstruction(roverName, instruction, steps);

        marsRoverApi.executeMoveInstructions(simulationId.toString(), roverMoves, LogginRoverMovePresenter.INSTANCE);
    }

    @When("We give the Rover {string} the Instructions")
    public void weGiveTheRoverTheInstructions(String roverName, io.cucumber.datatable.DataTable dataTable) {

        final InstructionBatch instructionBatch = parseSingleRoverInstructionsDataTable(roverName, dataTable);

        marsRoverApi.executeMoveInstructions(simulationId.toString(), instructionBatch, LogginRoverMovePresenter.INSTANCE);
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
        final SimulationSnapshot snapshot = simulationQuery.getSimulationInformation(simulationId);
        Assertions.assertEquals(0, snapshot.roverList().size());
    }

    @When("We give the Rovers the Instructions")
    public void weGiveTheRoversTheInstructions(io.cucumber.datatable.DataTable dataTable) {
        final InstructionBatch roverMoves = parseMultipleRoverInstructionsDataTable(dataTable);
        marsRoverApi.executeMoveInstructions(String.valueOf(simulationId), roverMoves, LogginRoverMovePresenter.INSTANCE);
    }

    @And("The timeline is")
    public void theTimeLineIs(io.cucumber.datatable.DataTable dataTable) {

    }

    private RoverState getRoverState(String roverId) {
        final SimulationSnapshot snapshot = simulationQuery.getSimulationInformation(simulationId);
        return snapshot.getRover(roverId).orElseThrow();
    }

    @When("We create a simulation of size {int}")
    public void weCreateASimulationOfSize(int simSize) {
        marsRoverApi.initializeSimulation(simSize, LoggingSimulationCreationPresenter.INSTANCE);
    }

    @And("This simulation has a total of {int} possible coordinates")
    public void thisSimulationHasATotalOfPossibleCoordinates(int totalCoordinates) {
        List<SimulationSnapshot> simulationSnapshots = simulationRepository.getSimulationSnapshots().orElseThrow();

        Assertions.assertEquals(totalCoordinates, simulationSnapshots.getFirst().totalCoordinates());
    }

    @And("The first simulation has a size of {int} and {int} possible coordinates")
    public void theFirstSimulationHasASizeOfAndPossibleCoordinates(int simSize, int totalCoordinates) {
        List<SimulationSnapshot> allSnapshots = simulationRepository.getSimulationSnapshots().orElseThrow().stream().toList();
        for (SimulationSnapshot snapshot : allSnapshots) {
            simulationMap.put(snapshot.simulationSize(), snapshot.id());
        }

        SimulationId firstSimId = simulationMap.get(simSize);
        SimulationSnapshot firstSimulationSnapshot = simulationQuery.getSimulationInformation(firstSimId);

        Assertions.assertEquals(simSize, firstSimulationSnapshot.simulationSize());
        Assertions.assertEquals(totalCoordinates, firstSimulationSnapshot.totalCoordinates());
    }

    @And("The second simulation has a size of {int} and {int} possible coordinates")
    public void theSecondSimulationHasASizeOfAndPossibleCoordinates(int simSize, int totalCoordinates) {
        SimulationId secondSimId = simulationMap.get(simSize);
        SimulationSnapshot secondSimulationSnapshot = simulationQuery.getSimulationInformation(secondSimId);

        Assertions.assertEquals(simSize, secondSimulationSnapshot.simulationSize());
        Assertions.assertEquals(totalCoordinates, secondSimulationSnapshot.totalCoordinates());
    }

    @Then("the system contains {int} Simulation with size {int}")
    public void theSystemContainsSimulationWithSize(int nOSimulations, int simSize) {
        Optional<List<SimulationSnapshot>> simulationSnapshots = simulationRepository.getSimulationSnapshots();

        Assertions.assertEquals(nOSimulations, simulationSnapshots.orElseThrow().size());
        Assertions.assertEquals(simSize, simulationSnapshots.orElseThrow().getFirst().simulationSize());
    }

    @Then("the system contains {int} Simulations")
    public void theSystemContainsSimulations(int nOSimulations) {
        Optional<List<SimulationSnapshot>> simulationSnapshots = simulationRepository.getSimulationSnapshots();
        Assertions.assertEquals(nOSimulations, simulationSnapshots.orElseThrow().size());
    }
}