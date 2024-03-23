import {Simulation} from "../../interfaces.ts";
import {useEffect, useState} from "react";
import '../../styles/SimulationSelection.css'
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
            <div className="simulationSelection">
                <WelcomeLogo />
                <SimulationSelectionOptions simulations={simulations}/>
            </div>
    )
}