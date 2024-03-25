import {Rover} from "../../../interfaces.ts";

function RoverInfo(rover: Rover) {
    return (
        <>
            <div>
                <h4>Rover {rover.roverName}:</h4>
                Position: ({rover.roverXPosition} - {rover.roverYPosition})<br/>
                Heading: {rover.roverHeading}<br/>
                Hitpoints: {rover.hitPoints}/5<br/>
                Status: {rover.operationalStatus}<br/>
            </div>
        </>
    )
}

export default RoverInfo