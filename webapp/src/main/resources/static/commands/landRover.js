import { getSimulationState } from "./getSimulationState.js";
import { moveModal, modalDiv, xCoordinateField, yCoordinateField, modalError, buildRoverInstructionControls } from "../index.js";

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
        modalDiv.innerHTML = `Cannot process these coordinates!`
        modalError()
    }
}


async function awaitFeedback(data){
    if(data.landingState === "ON_TOP"){
        await getSimulationState();
        modalDiv.innerHTML = "Rover landed on top!<br/>Both Rovers Broken!!"
        clearLandingFields();
        buildRoverInstructionControls()
        modalError();
    } else if(data.landingState === "SUCCESS"){
        await getSimulationState();
        modalDiv.innerHTML = `Rover has successfully landed.`
        clearLandingFields();
        buildRoverInstructionControls()
        moveModal();
    } 
    else if(data.landingState === "MISSES") {
        modalDiv.innerHTML = `The Rover missed the Simulation!<br/>Try again!`
        clearLandingFields();
        modalError()
    } else if (data.landingState === "UNSUCCESSFUL") {
        modalDiv.innerHTML = `The landing was unsuccessful`
        clearLandingFields();
        modalError()
    }
}

function clearLandingFields() {
    xCoordinateField.value = ""
    yCoordinateField.value = ""
}