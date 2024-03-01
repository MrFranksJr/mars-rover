import { drawMap, updateUIWithSimulationState, simulationIdDiv } from "../index.js"

export { getSimulationState, roversInSimulation }

let roversInSimulation;

async function getSimulationState(selectedSimulation) {
    const simulationState = await fetch(`/api/simulationstate/${selectedSimulation}`)
    const readableSimulationState = await simulationState.json()

    let roversInSimulationState = ""
    roversInSimulation = readableSimulationState.roverList
    if(roversInSimulation.length == 0){
        roversInSimulationState = "There are currently no active Rovers in the simulation"
        toggleRoverInstructionControls(true)
        updateUIWithSimulationState(readableSimulationState, roversInSimulationState);
        drawMap(readableSimulationState);
    } else {
        for (let rover of roversInSimulation) {
            roversInSimulationState += `<div><h4>Rover ${rover.roverName}: </h4>
                Position: (${rover.roverXPosition} - ${rover.roverYPosition})<br/>
                Heading: ${rover.roverHeading}<br/>
                Hitpoints: ${rover.hitPoints}/5<br/>
                Status: ${rover.operationalStatus}<br/>
                </div>
                `
        }
        toggleRoverInstructionControls(false)
        updateUIWithSimulationState(readableSimulationState, roversInSimulationState)
        drawMap(readableSimulationState)
    }

    return simulationState
}

function toggleRoverInstructionControls(state){
    moveRoverBtn.disabled = state;
}