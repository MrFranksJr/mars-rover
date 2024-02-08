import { drawMap, updateUIWithSimulationState, roverIdField, roverInstructionsField, xCoordinateField, yCoordinateField } from "/index.js"

export { getSimulationState }

async function getSimulationState() {
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()  
    let roversInSimulation;

    if(readableSimulationState.roverList.length == 0){
        roversInSimulation = 'There are currently no rovers in the simulation'
        updateUIWithSimulationState(readableSimulationState, roversInSimulation);
        drawMap(readableSimulationState);
        toggleRoverInstructionControls(true);
    } 
    else {
        disableLandControls()
        toggleRoverInstructionControls(false);
        disableRoverIde(readableSimulationState.roverList[0].roverName);
        roversInSimulation = `Rover ${readableSimulationState.roverList[0].roverName} 
                                is at position (${readableSimulationState.roverList[0].roverXPosition}, ${readableSimulationState.roverList[0].roverYPosition}) 
                                with heading ${readableSimulationState.roverList[0].roverHeading}`
        updateUIWithSimulationState(readableSimulationState, roversInSimulation);
        drawMap(readableSimulationState);
    }
    return simulationState;
}

function disableLandControls() {
    landRoverBtn.disabled = true;
    xCoordinateField.disabled = true;
    yCoordinateField.disabled = true;
}

function disableRoverIde(roverId){
    roverIdField.value = roverId;
    roverIdField.disabled = true;
}

function toggleRoverInstructionControls(state){
    moveRoverBtn.disabled = state;
    roverIdField.disabled = state;
    roverInstructionsField.disabled = state;
}