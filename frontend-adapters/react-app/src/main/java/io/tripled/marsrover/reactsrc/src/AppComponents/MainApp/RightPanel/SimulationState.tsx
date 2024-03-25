import {useContext} from "react";
import {SimulationContext} from "../../SimulationContext.tsx";
import RoverInfo from "./RoverInfo.tsx";

function SimulationState() {
    const {simulation} = useContext(SimulationContext);
    const roversInSimulation = simulation?.roverList;


    return (
        <>
            <div id="simulationState" className="simulationState">
                <h2>Current Simulation state:</h2>
                <p>The Simulation size is {simulation?.simulationSize}</p>
                <p>The Simulation has {simulation?.totalCoordinates} total Coordinates</p>
                <hr/>
                {roversInSimulation?.length === 0 ? (
                        <div className="rover-states">There are currently no Rovers in the Simulation</div>)
                    :
                    (roversInSimulation?.map(rover => (
                            <RoverInfo
                                key={rover.roverName}
                                roverName={rover.roverName}
                                roverHeading={rover.roverHeading}
                                roverXPosition={rover.roverXPosition}
                                roverYPosition={rover.roverYPosition}
                                hitPoints={rover.hitPoints}
                                operationalStatus={rover.operationalStatus}
                            />
                        ))
                    )}
            </div>
        </>
    )
}

export default SimulationState