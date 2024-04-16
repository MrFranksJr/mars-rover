import {Simulation} from "../../interfaces.ts";
import {useEffect, useState} from "react";
import { WelcomeLogo } from "../LandingScreen/WelcomeLogo.tsx";
import { SimulationSelectionOptions } from "./SimulationSelectionOptions.tsx";
import styles from '../../styles/SimulationSelectionOptions.module.css'

export default function SimulationSelection() {
    let [simulations, setSimulations] = useState<Array<Simulation>>([])

    useEffect(() => {
        fetch('/api/simulationstates')
            .then(response => response.json())
            .then(data => setSimulations(data))
    }, []);

    return (
        <div className={styles.main}>
            <div className={styles.simulationSelectionWindow}>
                <WelcomeLogo/>
                <SimulationSelectionOptions simulations={simulations}/>
            </div>
        </div>
    )
}