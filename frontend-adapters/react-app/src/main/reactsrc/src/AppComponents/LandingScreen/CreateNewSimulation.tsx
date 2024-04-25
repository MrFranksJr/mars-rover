import styles from "../../styles/SimulationSelectionOptions.module.css";
import {FormStateProps} from "../../interfaces.ts";
import {ChangeEvent, FormEvent, useContext, useState} from "react";
import {SimulationContext} from "../SimulationContext.tsx";
import {useNavigate} from "react-router";

interface FormData {
    simulationName: string;
    simulationSize: number;
}

function CreateNewSimulation({formState, formSwitch, getSimulation}: FormStateProps & {
    getSimulation: (simulationId: string) => Promise<any> }) {

    const {setSimulation} = useContext(SimulationContext);
    const navigate = useNavigate();

    const [formData, setFormData] = useState<FormData>({
        simulationName: "",
        simulationSize: NaN
    });

    const handleSimulationNameChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        console.log(name, value)
        setFormData((prevState) => ({...prevState, [name]: value}));
    }

    const handleSimulationSizeChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        console.log(name, value)
        setFormData((prevState) => ({...prevState, [name]: value}));
    }

    const handleCreateNewSimulation = async (event: FormEvent<HTMLFormElement> | any, buttonId: string) => {
        event.preventDefault()
        if (buttonId === "cancelBtn") {
            formSwitch();
            setFormData({simulationName: "", simulationSize: 0});
        } else {
            if (formData.simulationName === "") {
                console.error("You need to fill out a name for the simulation!")
            } else {
                try {
                    const response = await fetch(`/api/createsimulation/${formData.simulationSize}/${formData.simulationName}`, {
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
                setFormData({simulationName: "", simulationSize: 0});
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
                    <label htmlFor="simulationNameInput" className={styles.labelStyle}>Simulation name</label>
                    <input name="simulationName" id="simulationNameInput"
                           className={styles.simulationSelector}
                           placeholder="enter Simulation name"
                           value={formData.simulationName}
                           onChange={handleSimulationNameChange}
                           required/>
                    <label htmlFor="simulationSizeInput" className={styles.labelStyle}>Simulation size</label>
                    <input name="simulationSize"
                           id="simulationSizeInput"
                           className={styles.simulationSelector}
                           placeholder="define the simulation size"
                           type="number"
                           min={0}
                           max={50}
                           value={formData.simulationSize}
                           onChange={handleSimulationSizeChange}
                           required/>

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