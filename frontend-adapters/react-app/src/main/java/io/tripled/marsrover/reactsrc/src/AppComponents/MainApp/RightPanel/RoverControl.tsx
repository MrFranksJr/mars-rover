import {Rover} from "../../../interfaces.ts";
import styles from "../../../styles/RoverControl.module.css"

function RoverControl(rover: Rover) {
    if (rover.operationalStatus === "OPERATIONAL") {
        return (
            <>
                <div className={styles.singleRoverInstruction}>
                    <label className={styles.instructionLabel} htmlFor={rover.roverName}>{rover.roverName}</label>
                    <input id={rover.roverName} name={rover.roverName} className={styles.roverInstructions}
                           placeholder="Enter move instructions" type="text"/>
                </div>
            </>
        )
    } else {
        return (
            <>
                <div className={styles.singleRoverInstruction}>
                    <label className={styles.instructionLabel} htmlFor={rover.roverName}>{rover.roverName}</label>
                    <input id={rover.roverName} name={rover.roverName} className={styles.roverInstructions}
                           placeholder="Rover broken" disabled type="text"/>
                </div>
            </>
        )
    }
}

export default RoverControl