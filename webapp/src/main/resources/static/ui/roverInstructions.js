import { roversInSimulation } from "../commands/getSimulationState.js";
import { roverInstructionFieldsDiv, moveRoverBtn } from "../ui/htmlElements.js"

export { buildRoverInstructionControls }

async function buildRoverInstructionControls() {
    if (roversInSimulation.length === 0) {
        roverInstructionFieldsDiv.innerHTML = `There are currently no active Rovers in the Simulation.<br/>Land some Rovers first!`;
        moveRoverBtn.classList.add("hidden");
    }
    if (roversInSimulation.length !== 0) {
        let moveControlsHtml = ""
        for (let rover of roversInSimulation) {
            if (rover.operationalStatus === "OPERATIONAL") {
                moveControlsHtml += `
                <div class="singleRoverInstruction">
                    <label for="${rover.roverName}">${rover.roverName}</label>
                    <input id="${rover.roverName}" name="${rover.roverName}" class="roverInstructions" placeholder="Enter move instructions" type="text">
                </div>
                `
            } else {
                moveControlsHtml += `
                <div class="singleRoverInstruction">
                    <label for="${rover.roverName}">${rover.roverName}</label>
                    <input id="${rover.roverName}" name="${rover.roverName}" class="roverInstructions" placeholder="Rover broken" disabled type="text">
                </div>
                `
            }
        }
        roverInstructionFieldsDiv.innerHTML = moveControlsHtml;
        moveRoverBtn.classList.remove("hidden");
    }
}