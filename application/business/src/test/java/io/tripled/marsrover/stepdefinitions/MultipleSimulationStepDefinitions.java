package io.tripled.marsrover.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MultipleSimulationStepDefinitions {
    private final SimulationRepository simulationRepository;
    private final MarsRoverApi marsRoverApi;
    private final SimulationQuery simulationQuery;
    private final HashMap<Integer, SimulationId> simulationMap = new HashMap<>();

    public MultipleSimulationStepDefinitions() {
        simulationRepository = new DummySimulationRepository();
        marsRoverApi = new MarsRoverController(simulationRepository);
        simulationQuery = (SimulationQuery) simulationRepository;
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
