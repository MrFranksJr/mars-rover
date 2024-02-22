import { generateMap } from "./data/mapData.js";
import { landRover } from "./commands/landRover.js";
import { moveRover } from "./commands/moveRover.js";
import { createSimulation } from "./commands/createSimulation.js";
import { getSimulationState, roversInSimulation } from "./commands/getSimulationState.js";
import { showAllAnimations, disableAnimations } from "./animations/animations.js";

export {drawMap, updateUIWithSimulationState, moveModal, modalDiv, modalError, roverIdField, xCoordinateField, yCoordinateField, buildRoverInstructionControls}

async function onLoadCreateSimulation(){
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()
    
    if( readableSimulationState.simulationSize == -1 ){
        await createSimulation();
        await getSimulationState();
    } else {
        await getSimulationState();
    }
}

async function buildRoverInstructionControls() {
    if (roversInSimulation.length === 0) {
        roverInstructionFieldsDiv.innerHTML = "There are currently no active Rovers in the Simulation.<br/>Land some Rovers first!";
    }
    if (roversInSimulation.length !== 0) {
        let moveControlsHtml = ""
        for (let rover of roversInSimulation) {
            if (rover.operationalStatus === "OPERATIONAL") {
                moveControlsHtml += `
                <div class="singleRoverInstruction">
                    <label for="${rover.roverName}">${rover.roverName}</label>
                    <input id="${rover.roverName}-roverInstructions" name="${rover.roverName}" class="roverInstructions" placeholder="Enter move instructions" type="text" pattern="(^[frbl]\\d*)$">
                </div>
                `
            } else {
                moveControlsHtml += `
                <div class="singleRoverInstruction">
                    <label for="${rover.roverName}">${rover.roverName}</label>
                    <input id="${rover.roverName}-roverInstructions" name="${rover.roverName}" class="roverInstructions" placeholder="Rover broken" disabled type="text" pattern="(^[frbl]\\d*)$">
                </div>
                `
            }
            
        }
        roverInstructionFieldsDiv.innerHTML = moveControlsHtml;

    }
}

async function buildPage(){
    await onLoadCreateSimulation();
    await buildRoverInstructionControls();
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
const roverIdField = document.getElementById('roverId')
const simulationStateField = document.getElementById('simulationState')
const xCoordinateField = document.getElementById('roverXCoordinate')
const yCoordinateField = document.getElementById('roverYCoordinate')
const simulationMapDiv = document.getElementById('simulationMap')
const roverInstructionFieldsDiv = document.getElementById('roverInstructionFields')

///////EVENT LISTENERS
landRoverBtn.addEventListener('click', landRover)
moveRoverBtn.addEventListener('click', moveRover)
document.querySelectorAll('form').forEach(node => {
    node.addEventListener("submit", function (event) {
        event.preventDefault()
    })
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