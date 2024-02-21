import { drawMap, updateUIWithSimulationState } from "../index.js"

export { getSimulationState, roversInSimulation }

let roversInSimulation;

async function getSimulationState() {
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()  
    let simulationStateString = "";
    roversInSimulation = readableSimulationState.roverList

    if(readableSimulationState.roverList.length == 0){
        simulationStateString = "There are currently no active Rovers in the simulation"
        toggleRoverInstructionControls(true);
        updateUIWithSimulationState(readableSimulationState, simulationStateString);
        drawMap(readableSimulationState);
    } 
    else {
        for (let rover of roversInSimulation) {
            simulationStateString += `<strong>Rover</strong> ${rover.roverName}: 
                <span class="strong-italic">Position:</span> (${rover.roverXPosition}, ${rover.roverYPosition}) <br/>
                <span class="strong-italic">Heading:</span> ${rover.roverHeading}<br/>
                <span class="strong-italic">Hitpoints:</span> ${rover.hitPoints}<br/>
                <span class="strong-italic">Status:</span> ${rover.operationalStatus}<br/>
                <br/>
                `
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