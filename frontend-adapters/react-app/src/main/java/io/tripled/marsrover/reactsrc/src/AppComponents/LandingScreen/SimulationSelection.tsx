import {Simulation} from "../../interfaces.ts";
import {useEffect, useState} from "react";
import styles from '../../styles/SimulationSelectionOptions.module.css'
import { WelcomeLogo } from "../LandingScreen/WelcomeLogo.tsx";
import {SimulationSelectionOptions} from "./SimulationSelectionOptions.tsx";

export default function SimulationSelection() {
    let [simulations, setSimulations] = useState<Array<Simulation>>([])

    useEffect(() => {
        fetch('/api/simulationstates')
            .then(response => response.json())
            .then(data => setSimulations(data))
    }, []);

    return (
            <div className={styles.simulationSelection}>
                <WelcomeLogo />
                <SimulationSelectionOptions simulations={simulations}/>
            </div>
    )
}