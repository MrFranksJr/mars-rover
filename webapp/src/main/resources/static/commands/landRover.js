import { getSimulationState } from "/commands/getSimulationState.js";
import { moveModal, modalDiv, xCoordinateField, yCoordinateField, modalError } from "/index.js";

export { landRover }

async function landRover(){
    const xCoordinate = xCoordinateField.value;
    const yCoordinate = yCoordinateField.value;
    let result = await fetch(`/api/landrover/${xCoordinate}/${yCoordinate}` , {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });
    
    let data = await result.json();

    await awaitFeedback(data);
}

async function awaitFeedback(data){
    console.log(data.result)
    if(data.result == "Landing successful"){
        await getSimulationState();
        modalDiv.innerHTML = "Your rover has successfully landed."
        moveModal();
    } 
    else if(data.result == "Landing unsuccessful") {
        modalDiv.innerHTML = `The Rover missed the Simulation!<br/>Try again!`
        modalError()
    }
}



