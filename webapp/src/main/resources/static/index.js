import { generateMap } from "/data/mapData.js";
import { landRover } from "/commands/landRover.js";
import { moveRover } from "/commands/moveRover.js";
import { createSimulation } from "/commands/createSimulation.js";
import { getSimulationState } from "/commands/getSimulationState.js";

export {drawMap, updateUIWithSimulationState, moveModal, modalDiv, modalError}


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


setTimeout(() => {
    onLoadCreateSimulation();
  }, "500");

function updateUIWithSimulationState(readableSimulationState, roversInSimulation){
    document.getElementById('simulationState').innerHTML = `
        <p>The simuation Size is ${readableSimulationState.simulationSize}</p>
        <p>The simulation has ${readableSimulationState.totalCoordinates} total Coordinates</p>
        <p>${roversInSimulation}</p>
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
    document.getElementById('simulationMap').innerHTML =  generateMap(readableSimulationState)
}

///////ELEMENTS
const landRoverBtn = document.getElementById('landRoverBtn');
const moveRoverBtn = document.getElementById('moveRoverBtn');
const modalDiv = document.getElementById('feedbackModal');

///////EVENT LISTENERS
landRoverBtn.addEventListener('click', landRover)
moveRoverBtn.addEventListener('click', moveRover)
document.querySelectorAll('form').forEach(node => {
    node.addEventListener("submit", function (event) {
        event.preventDefault()
    })
})

///////write footer
document.getElementById('copyright').innerHTML = "\xA9" + new Date().getFullYear() + "\xa0<img src=\"images/TripleD.svg\" class=\"tripled-logo\"> Mars Rover Association";