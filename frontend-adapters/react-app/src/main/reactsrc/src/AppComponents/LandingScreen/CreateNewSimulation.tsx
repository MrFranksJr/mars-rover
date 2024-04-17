import styles from "../../styles/SimulationSelectionOptions.module.css";
import {FormStateProps} from "../../interfaces.ts";
import {ChangeEvent, FormEvent, useContext, useState} from "react";
import {SimulationContext} from "../SimulationContext.tsx";
import {useNavigate} from "react-router";

interface FormData {
    simulationName: string;
}

function CreateNewSimulation({formState, formSwitch, getSimulation}: FormStateProps & {
    getSimulation: (simulationId: string) => Promise<any>
}) {

    const {setSimulation} = useContext(SimulationContext);
    const navigate = useNavigate();

    const [formData, setFormData] = useState<FormData>({
        simulationName: "",
    });

    const handleSimulationNameChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        setFormData((prevState) => ({...prevState, [name]: value}));
    }

    const handleCreateNewSimulation = async (event: FormEvent<HTMLFormElement> | any, buttonId: string) => {
        event.preventDefault()
        if (buttonId === "cancelBtn") {
            formSwitch();
            setFormData({simulationName: ""});
        } else {
            if (formData.simulationName === "") {
                console.error("You need to fill out a name for the simulation!")
            } else {
                try {
                    const response = await fetch(`/api/createsimulation/10/${formData.simulationName}`, {
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'}
                    });
                    const data = await response.json();
                    const simulationId = data.simulationId;
                    const simulationData = await getSimulation(simulationId)
                    setSimulation(simulationData)
                    localStorage.setItem('simulationId', JSON.stringify(simulationId));
                    navigate('/app');
                } catch (error) {
                    console.error("Error creating new simulation:", error);
                }
                setFormData({simulationName: ""});
            }
        }
    }

    if (formState === "INIT") {
        return (
            <>
                <div className={styles.newSimulation}>
                    <label className={styles.labelStyle}>Create new Simulation</label>
                    <button onClick={formSwitch} className={styles.createSimulationBtn}>Create...</button>
                </div>
            </>
        )
    } else {
        return (
            <>
                <form className={styles.simulationSelectionForm}
                      onSubmit={(event) => handleCreateNewSimulation(event, '')}>
                    <label htmlFor="simulationNameInput" className={styles.labelStyle}>Create new Simulation</label>
                    <input name="simulationName" id="simulationNameInput" className={styles.simulationSelector}
                           placeholder="enter Simulation name" value={formData.simulationName}
                           onChange={handleSimulationNameChange} required/>

                    <button className={styles.createSimulationBtn} type="submit"
                            onClick={(event) => handleCreateNewSimulation(event, 'createBtn')}>Create...
                    </button>
                    <button className={styles.cancelCreateSimulationButton} type="submit"
                            onClick={(event) => handleCreateNewSimulation(event, 'cancelBtn')}>Cancel
                    </button>
                </form>
            </>
        )
    }
}

export default CreateNewSimulation