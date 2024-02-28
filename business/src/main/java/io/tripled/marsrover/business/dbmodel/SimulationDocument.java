package io.tripled.marsrover.business.dbmodel;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("simulationrepository")
public class SimulationDocument {
    @Id
    private String id;
    private int simulationSize;


    private List<RoverState> roverList;
    private int nrOfRovers;


    public SimulationDocument(Simulation simulation) {
        SimulationSnapshot snapshot = simulation.takeSnapshot();
        this.id = snapshot.id().id().toString();
        this.simulationSize = snapshot.simulationSize();
        this.roverList = snapshot.roverList();
        this.nrOfRovers = roverList.size();
    }

    public SimulationDocument() {
    }

    public SimulationId getId() {
        return new SimulationId(UUID.fromString(id));
    }

    public int getSimulationSize() {
        return simulationSize;
    }

    public int getNrOfRovers() {
        return nrOfRovers;
    }

    public void setNrOfRovers(int nrOfRovers) {
        this.nrOfRovers = nrOfRovers;
    }

    public List<RoverState> getRoverList() {
        return roverList;
    }
    @Override
    public String toString() {
        return "SimulationDocument{" +
                "id='" + id + '\'' +
                ", simulationSize=" + simulationSize +
                ", roverList=" + roverList +
                ", nrOfRovers=" + nrOfRovers +
                '}';
    }
}
