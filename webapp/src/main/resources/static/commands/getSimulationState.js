import { drawMap, updateUIWithSimulationState } from "/index.js"

export { getSimulationState }


async function getSimulationState() {
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()  
    let roversInSimulation;

    if(readableSimulationState.roverList.length == 0){
        console.log('no rovers in list');
        roversInSimulation = 'There are currently no rovers in the simulation'
        updateUIWithSimulationState(readableSimulationState, roversInSimulation);
        drawMap(readableSimulationState);
        
        toggleRoverInstructionControls(true);
    } else {
        disableLandControls()
        toggleRoverInstructionControls(false);
        disableRoverIde(readableSimulationState.roverList[0].roverName);
        roversInSimulation = `Rover ${readableSimulationState.roverList[0].roverName} is at position (${readableSimulationState.roverList[0].roverXPosition}, ${readableSimulationState.roverList[0].roverYPosition}) with heading ${readableSimulationState.roverList[0].roverHeading}`
        updateUIWithSimulationState(readableSimulationState, roversInSimulation);
        drawMap(readableSimulationState);
    }
    return simulationState;
}

function disableLandControls() {
    landRoverBtn.disabled = true;
    document.getElementById('roverXCoordinate').disabled = true;
    document.getElementById('roverYCoordinate').disabled = true;
}



function disableRoverIde(roverId){
    document.getElementById('roverId').value = roverId;
    document.getElementById('roverId').disabled = true;
}

function toggleRoverInstructionControls(state){
    console.log(`the state is ${state}`)
    document.getElementById('moveRoverBtn').disabled = state;
    document.getElementById('roverId').disabled = state;
    document.getElementById('roverInstructions').disabled = state;
}