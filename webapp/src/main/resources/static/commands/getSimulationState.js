import { drawMap, updateUIWithSimulationState, simulationIdDiv } from "../index.js"

export { getSimulationState, roversInSimulation }

let roversInSimulation;

async function getSimulationState() {
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()  
    let simulationStateString = ""
    roversInSimulation = readableSimulationState.roverList

    if(readableSimulationState.roverList.length == 0){
        simulationStateString = "There are currently no active Rovers in the simulation"
        toggleRoverInstructionControls(true)
        updateUIWithSimulationState(readableSimulationState, simulationStateString);
        drawMap(readableSimulationState);
    } else {
        for (let rover of roversInSimulation) {
            simulationStateString += `<div><h4>Rover ${rover.roverName}: </h4>
                Position: (${rover.roverXPosition} - ${rover.roverYPosition})<br/>
                Heading: ${rover.roverHeading}<br/>
                Hitpoints: ${rover.hitPoints}/5<br/>
                Status: ${rover.operationalStatus}<br/>
                </div>
                `
        }
        simulationIdDiv.innerText = readableSimulationState.simulationId
        toggleRoverInstructionControls(false)
        updateUIWithSimulationState(readableSimulationState, simulationStateString)
        drawMap(readableSimulationState)
    }

    return simulationState
}

function toggleRoverInstructionControls(state){
    moveRoverBtn.disabled = state;
}