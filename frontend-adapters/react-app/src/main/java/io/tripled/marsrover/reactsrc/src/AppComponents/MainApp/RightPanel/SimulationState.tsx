import {useContext} from "react";
import {SimulationContext} from "../../SimulationContext.tsx";

function SimulationState() {
    const {simulation} = useContext(SimulationContext)

    return (
        <>
            <div id="simulationState" className="simulationState">
                <h2>Current Simulation state:</h2>
                <p>The Simulation size is {simulation?.simulationSize}</p>
                <p>The Simulation has {simulation?.totalCoordinates} total Coordinates</p>
                <hr/>
                <div className="rover-states">ROVERS HERE</div>
            </div>
        </>
    )
}

export default SimulationState