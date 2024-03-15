import { landRover } from "./commands/landRover.js"
import { moveRover } from "./commands/moveRover.js"
import { createSimulation } from "./commands/createSimulation.js"
import { getSimulationState } from "./commands/getSimulationState.js"
import { showAllAnimations, disableAnimations } from "./animations/animations.js"
import { infoModal } from "./ui/infoModal.js"
import { buildRoverInstructionControls } from "./ui/roverInstructions.js"
import { infoCloseBtn, simulationsDropDown, simulationStateField } from "./ui/htmlElements.js"

export {updateUIWithSimulationState, buildRoverInstructionControls }

async function buildPage(){
    await onLoadCreateSimulation("loadFirst");
    await buildRoverInstructionControls();
}

async function onLoadCreateSimulation(toLoad){
    let simulationStates = await fetch('/api/simulationstates')
    let readableSimulationStates = await simulationStates.json()
    if(readableSimulationStates.length == 0){
        await createSimulation();
        await onLoadCreateSimulation();
    } else {
        const simulationSelectionField = document.getElementById('simulations')
        if (toLoad == "loadFirst") {
            buildSimulationSelector(readableSimulationStates, toLoad)
        } else {
            buildSimulationSelector(readableSimulationStates, toLoad)
        }
        const simulationId = simulationSelectionField.value;

        await getSimulationState(simulationId);
    }
}

function updateUIWithSimulationState(readableSimulationState, simulationStateString){
    simulationStateField.innerHTML = `
        <h2>Current Simulation state:</h2>
        <p>The simuation Size is ${readableSimulationState.simulationSize}</p>
        <p>The simulation has ${readableSimulationState.totalCoordinates} total Coordinates</p>
        <hr/>
        <div class="rover-states">${simulationStateString}</div>
        `
}

function buildSimulationSelector(readableSimulationState, toLoad){
    let optionList = "";
    if (toLoad == "loadFirst") {
        for(let simulationState of readableSimulationState){
            optionList += `<option id="${simulationState.simulationId}" value="${simulationState.simulationId}">${simulationState.simulationId}</option>`
        }
    } else {
        for(let simulationState of readableSimulationState){
             if (simulationState.simulationId == toLoad) {
                optionList += `<option selected id="${simulationState.simulationId}" value="${simulationState.simulationId}">${simulationState.simulationId}</option>`
            } else {
                optionList += `<option id="${simulationState.simulationId}" value="${simulationState.simulationId}">${simulationState.simulationId}</option>`
            }
        }
    }
    optionList += `<option id="newSimulationOption" value="newSimulation">Create new...</option>`
    document.getElementById("simulations").innerHTML = optionList;
}

///////EVENT LISTENERS
infoBtn.addEventListener('click', infoModal)
infoCloseBtn.addEventListener('click', infoModal)
landRoverBtn.addEventListener('click', landRover)
moveRoverBtn.addEventListener('click', moveRover)
document.querySelectorAll('form').forEach(node => {
    node.addEventListener("submit", function (event) {
        event.preventDefault()
    })
})
simulationsDropDown.addEventListener('change', async function () {
    if (simulationsDropDown.value == "newSimulation") {
        let createdId = await createSimulation()
        await onLoadCreateSimulation(createdId)
    }
    await onLoadCreateSimulation(simulationsDropDown.value)
    await buildRoverInstructionControls();
})


if(true) {
    showAllAnimations()
} else {
    disableAnimations()
}
setTimeout(() => {
    buildPage();
}, "1000");
///////write footer


document.getElementById('copyright').innerHTML = "\xA9" + new Date().getFullYear() + "\xa0<img src=\"images/TripleD.svg\" class=\"tripled-logo\"> Mars Rover Association"