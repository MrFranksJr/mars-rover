import styles from '../../../styles/RoverControls.module.css'
import {useContext} from "react";
import {SimulationContext} from "../../SimulationContext.tsx";
import RoverControl from "./RoverControl.tsx";

function RoverControls() {
    const {simulation} = useContext(SimulationContext);
    const roversInSimulation = simulation?.roverList;

    return (
        <>
            <form id="moveRoverControls" className={styles.moveRoverControls}>
                <div id="roverInstructionFields" className={styles.roverInstructionFields}>
                    {roversInSimulation?.length === 0 ? (
                            `There are currently no active Rovers in the Simulation. Land some Rovers first!`)
                        :
                        (roversInSimulation?.map(rover => (
                                <RoverControl
                                    key={rover.roverName}
                                    roverName={rover.roverName}
                                    roverHeading={rover.roverHeading}
                                    roverXPosition={rover.roverXPosition}
                                    roverYPosition={rover.roverYPosition}
                                    hitPoints={rover.hitPoints}
                                    operationalStatus={rover.operationalStatus}
                                />
                            ))
                        )}
                </div>
                <button id="moveRoverBtn" className={styles.moveRoverBtn}>Execute Instructions!</button>
            </form>
        </>
    )
}

export default RoverControls