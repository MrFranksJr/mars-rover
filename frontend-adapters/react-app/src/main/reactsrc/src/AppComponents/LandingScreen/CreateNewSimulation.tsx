import styles from "../../styles/SimulationSelectionOptions.module.css";
import { Props } from "../../interfaces.ts";
import {FormEvent} from "react";

function CreateNewSimulation({formState, formSwitch}: Props) {
    const handleCreateNewSimulation = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        console.log("CLICKED!")
    }

    if (formState === "INIT") {
        return (
            <>
                <div className={styles.newSimulation}>
                    <label className={styles.labelStyle}>Create a new Simulation</label>
                    <button onClick={formSwitch} className={styles.createSimulationBtn}>Create...</button>
                </div>
            </>
        )
    }
    else {
        return (
            <>
                <form className={styles.simulationSelectionForm} onSubmit={handleCreateNewSimulation}>
                    <label htmlFor="simulationNameInput" className={styles.labelStyle}>Create new Simulation</label>
                    <input name="simulationNameInput" id="simulationNameInput" className={styles.simulationSelector} placeholder="enter Simulation name"/>

                    <button className={styles.createSimulationBtn} type="submit">Create...</button>
                    <button onClick={formSwitch} className={styles.cancelCreateSimulationButton}>Cancel</button>
                </form>
            </>
        )
    }
}

export default CreateNewSimulation