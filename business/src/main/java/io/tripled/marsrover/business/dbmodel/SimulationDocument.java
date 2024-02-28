package io.tripled.marsrover.business.dbmodel;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("simulationrepository")
public class SimulationDocument {


//    private final UUID id;
    @Id
    private final ObjectId _id;
    private final int simulationSize;
    private final List<RoverState> roverList;
    private int nrOfRovers;

    public SimulationDocument(ObjectId _id, int simulationSize, List<RoverState> roverList) {
        super();
//        this.id = id;
        this._id = _id;
        this.simulationSize = simulationSize;
        this.roverList = roverList;
        this.nrOfRovers = roverList.size();
    }

    public SimulationDocument(Simulation simulation) {
        super();
        SimulationSnapshot snapshot = simulation.takeSnapshot();
        this._id = snapshot.id();
//        this.id = snapshot.id();
        this.simulationSize = snapshot.simulationSize();
        this.roverList = snapshot.roverList();
        this.nrOfRovers = roverList.size();
    }

//    public UUID getId() {
//        return id;
//    }
    public ObjectId getId() {
        return _id;
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
}
