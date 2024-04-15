package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.api.rover.RoverState;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("simulationrepository")
public class SimulationDocument {
    @Id
    private String id;
    private String simulationName;
    private int simulationSize;
    private List<RoverState> roverList;
    private int nrOfRovers;


    public SimulationDocument(SimulationSnapshot snapshot) {
        this.id = snapshot.id().id().toString();
        this.simulationName = snapshot.simulationName();
        this.simulationSize = snapshot.simulationSize();
        this.roverList = snapshot.roverList();
        this.nrOfRovers = roverList.size();
    }

    public SimulationDocument() {
    }

    public SimulationId getId() {
        return new SimulationId(UUID.fromString(id));
    }

    public String getSimulationName() { return simulationName; }

    public int getSimulationSize() {
        return simulationSize;
    }

    public List<RoverState> getRoverList() {
        return roverList;
    }

    @Override
    public String toString() {
        return "SimulationDocument{" +
                "id='" + id + '\'' +
                ", simulationName=" + simulationName +
                ", simulationSize=" + simulationSize +
                ", roverList=" + roverList +
                ", nrOfRovers=" + nrOfRovers +
                '}';
    }
}
