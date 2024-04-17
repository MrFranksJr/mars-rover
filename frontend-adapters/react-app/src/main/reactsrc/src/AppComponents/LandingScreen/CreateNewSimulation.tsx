import styles from "../../styles/SimulationSelectionOptions.module.css";
import {FormStateProps} from "../../interfaces.ts";
import {FormEvent} from "react";

function CreateNewSimulation({formState, formSwitch}: FormStateProps) {
    const handleCreateNewSimulation = (event: FormEvent<HTMLFormElement>, buttonId: string) => {
        event.preventDefault()
        if (buttonId === "cancelBtn") {
            formSwitch();
        } else {

        }
        console.log("CLICKED!" + buttonId)
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
                <form className={styles.simulationSelectionForm} onSubmit={(event) => handleCreateNewSimulation(event, '')}>
                    <label htmlFor="simulationNameInput" className={styles.labelStyle}>Create new Simulation</label>
                    <input name="simulationNameInput" id="simulationNameInput" className={styles.simulationSelector} placeholder="enter Simulation name"/>

                    <button className={styles.createSimulationBtn} type="submit" onClick={(event) => handleCreateNewSimulation(event, 'createBtn')}>Create...</button>
                    <button className={styles.cancelCreateSimulationButton} type="submit" onClick={(event) => handleCreateNewSimulation(event, 'cancelBtn')}>Cancel</button>
                </form>
            </>
        )
    }
}

export default CreateNewSimulation