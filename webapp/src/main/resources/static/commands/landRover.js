import { getSimulationState } from "/commands/getSimulationState.js";
import { moveModal, modalDiv } from "/index.js";

export { landRover }

async function landRover(){
    const xCoordinate = document.getElementById("roverXCoordinate").value;
    const yCoordinate = document.getElementById("roverYCoordinate").value;
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
    console.log(JSON.stringify(data));
    if(JSON.stringify(data) == "{\"result\":\"Landing successful\"}"){
        await getSimulationState();
        modalDiv.innerHTML = "Your rover has successfully landed."
        moveModal();
    } else {
        modalDiv.innerHTML = "Your rover has not landed."
        moveModal();
    }
}



