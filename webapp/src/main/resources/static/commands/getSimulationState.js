import { drawMap, updateUIWithSimulationState, roverIdField, roverInstructionsField} from "/index.js"

export { getSimulationState, roversInSimulation }

let roversInSimulation;

async function getSimulationState() {
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()  
    let simulationStateString = "";
    roversInSimulation = readableSimulationState.roverList

    if(readableSimulationState.roverList.length == 0){
        console.log('no active rovers')
        simulationStateString = "There are currently no active Rovers in the simulation"
        toggleRoverInstructionControls(true);
        updateUIWithSimulationState(readableSimulationState, simulationStateString);
        drawMap(readableSimulationState);
    } 
    else {
        console.log('redraw map')
        for (let rover of roversInSimulation) {
            simulationStateString += `Rover ${rover.roverName} 
                is at position (${rover.roverXPosition}, ${rover.roverYPosition}) 
                with heading ${rover.roverHeading} <br/>`
        }
        toggleRoverInstructionControls(false);
        updateUIWithSimulationState(readableSimulationState, simulationStateString);
        drawMap(readableSimulationState);
    }

    return simulationState;
}

function toggleRoverInstructionControls(state){
    moveRoverBtn.disabled = state;
}