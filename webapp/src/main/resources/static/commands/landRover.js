import { getSimulationState } from "/commands/getSimulationState.js";
import { moveModal, modalDiv, xCoordinateField, yCoordinateField, modalError, buildRoverInstructionControls } from "/index.js";

export { landRover }

async function landRover(){
    const xCoordinate = xCoordinateField.value;
    const yCoordinate = yCoordinateField.value;

    if (xCoordinate && yCoordinate) {
        let result = await fetch(`/api/landrover/${xCoordinate}/${yCoordinate}` , {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });
        await checkForBadRequest(result)
    } 
    else {
        modalDiv.innerHTML = `X and/or Y coordinate(s) are empty!<br/>Make sure you enter proper land coordinates`
        modalError()
    }
}

async function checkForBadRequest(result) {
    if (result.ok) {
        let data = await result.json();
        await awaitFeedback(data);
    }
    else {
        modalDiv.innerHTML = `Cannot process these coordinates!<br/>Make sure you enter numbers within simulation bounds.`
        modalError()
    }
}


async function awaitFeedback(data){
    if(data.result == "Landing successful"){
        await getSimulationState();
        modalDiv.innerHTML = "Your rover has successfully landed."
        clearLandingFields();
        buildRoverInstructionControls()
        moveModal();
    } 
    else if(data.result == "Landing unsuccessful") {
        modalDiv.innerHTML = `The Rover missed the Simulation!<br/>Try again!`
        clearLandingFields();
        modalError()
    }
}

function clearLandingFields() {
    xCoordinateField.value = ""
    yCoordinateField.value = ""
}