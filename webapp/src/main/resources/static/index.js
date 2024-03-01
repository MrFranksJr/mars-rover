import { generateMap } from "./data/mapData.js";
import { landRover } from "./commands/landRover.js";
import { moveRover } from "./commands/moveRover.js";
import { createSimulation } from "./commands/createSimulation.js";
import { getSimulationState, roversInSimulation } from "./commands/getSimulationState.js";
import { showAllAnimations, disableAnimations } from "./animations/animations.js";
import {showInfo} from "./commands/showInfo.js";

export {drawMap, updateUIWithSimulationState, moveModal, modalDiv, modalError, xCoordinateField, yCoordinateField, simulationIdDiv, buildRoverInstructionControls}

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

async function buildRoverInstructionControls() {
    if (roversInSimulation.length === 0) {
        roverInstructionFieldsDiv.innerHTML = "There are currently no active Rovers in the Simulation.<br/>Land some Rovers first!";
        moveRoverBtn.classList.add("hidden");
    }
    if (roversInSimulation.length !== 0) {
        let moveControlsHtml = ""
        for (let rover of roversInSimulation) {
            if (rover.operationalStatus === "OPERATIONAL") {
                moveControlsHtml += `
                <div class="singleRoverInstruction">
                    <label for="${rover.roverName}">${rover.roverName}</label>
                    <input id="${rover.roverName}-roverInstructions" name="${rover.roverName}" class="roverInstructions" placeholder="Enter move instructions" type="text">
                </div>
                `
            } else {
                moveControlsHtml += `
                <div class="singleRoverInstruction">
                    <label for="${rover.roverName}">${rover.roverName}</label>
                    <input id="${rover.roverName}-roverInstructions" name="${rover.roverName}" class="roverInstructions" placeholder="Rover broken" disabled type="text">
                </div>
                `
            }
            
        }
        roverInstructionFieldsDiv.innerHTML = moveControlsHtml;
        moveRoverBtn.classList.remove("hidden");
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




function moveModal() {
    modalDiv.classList.add('activeModal');
    setTimeout(() => {
        modalDiv.classList.remove('activeModal');
      }, "2500");
}

function modalError() {
    modalDiv.classList.add('errorModal');
    setTimeout(() => {
        modalDiv.classList.remove('errorModal');
      }, "2500");
}

function drawMap(readableSimulationState) {
    simulationMapDiv.innerHTML =  "<div class='mapInnerDiv'>" + generateMap(readableSimulationState) + "</div>"
}

///////ELEMENTS
const landRoverBtn = document.getElementById('landRoverBtn')
const moveRoverBtn = document.getElementById('moveRoverBtn')
const modalDiv = document.getElementById('feedbackModal')
const simulationStateField = document.getElementById('simulationState')
const xCoordinateField = document.getElementById('roverXCoordinate')
const yCoordinateField = document.getElementById('roverYCoordinate')
const simulationMapDiv = document.getElementById('simulationMap')
const roverInstructionFieldsDiv = document.getElementById('roverInstructionFields')
const simulationIdDiv = document.getElementById('simulationId')
const infoBtn = document.getElementById('infoBtn')
const infoCloseBtn = document.getElementById('info-close-btn')
const simulationsDropDown = document.getElementById("simulations")

///////EVENT LISTENERS
infoBtn.addEventListener('click', showInfo)
infoCloseBtn.addEventListener('click', showInfo)
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



///////write footer
document.getElementById('copyright').innerHTML = "\xA9" + new Date().getFullYear() + "\xa0<img src=\"images/TripleD.svg\" class=\"tripled-logo\"> Mars Rover Association"


if(false) {
    showAllAnimations()
} else {
    disableAnimations()
}
setTimeout(() => {
    buildPage();
}, "1000");