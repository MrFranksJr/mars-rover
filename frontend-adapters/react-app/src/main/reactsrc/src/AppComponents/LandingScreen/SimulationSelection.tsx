import {Simulation} from "../../interfaces.ts";
import {useEffect, useState} from "react";
import {WelcomeLogo} from "./WelcomeLogo.tsx";
import {SimulationSelectionOptions} from "./SimulationSelectionOptions.tsx";
import styles from '../../styles/SimulationSelectionOptions.module.css'

export default function SimulationSelection() {
    let [simulations, setSimulations] = useState<Array<Simulation>>([])

    const [formState, setFormState] = useState("INIT")

    const formSwitch = () => {
        if (formState === "INIT") {
            setFormState("CREATE")
        }
        if (formState === "CREATE") {
            setFormState("INIT")
        }
    }

    useEffect(() => {
        fetch('/api/simulationstates')
            .then(response => response.json())
            .then(data => setSimulations(data))
    }, []);

    return (
        <div className={styles.main}>
            <div className={styles.simulationSelectionWindow}>
                <WelcomeLogo
                    formState={formState}
                 formSwitch={formSwitch}/>
                <SimulationSelectionOptions
                    simulations={simulations}
                    formState={formState}
                    formSwitch={formSwitch}
                />
            </div>
        </div>
    )
}