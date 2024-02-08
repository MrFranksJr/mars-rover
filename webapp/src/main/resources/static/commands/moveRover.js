import { getSimulationState } from "/commands/getSimulationState.js";
import { moveModal, modalDiv, modalError } from "/index.js";

export { moveRover }

async function moveRover(){
    const roverId = document.getElementById("roverId").value;
    const roverInstructions = document.getElementById("roverInstructions").value;

    if(roverId != null && roverInstructions != null ){
        //Check if rover instructions are correct
        const regex = /^(?:[frbl](?=.*\d)?\d* ?)*$/i
        const match = roverInstructions.match(regex)
        if (match) {
            await fetch(`/api/moverover/${roverId}/${roverInstructions}` , {
                method: "POST",
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                }});
        
            await getSimulationState();
    
            modalDiv.innerHTML = "Rover instructions successfully executed"
            document.getElementById('roverInstructions').value = "";
            moveModal();
        } 
        else {
        modalDiv.innerHTML = "Cannot execute these instructions..."
        document.getElementById('roverInstructions').value = "";
        modalError();
        }
    }
}


/*
(1) r f5 
(2) r f1 f1 f1 f1 f1 

1) split original string (1) in new string (2)
2) for loop based on spaces
3) for each element execute (+ timeout to see map change)
4)
 */