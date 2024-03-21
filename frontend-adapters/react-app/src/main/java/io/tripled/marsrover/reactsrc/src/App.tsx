import './App.css'
import Header from "./AppComponents/Header.tsx";
import Footer from "./AppComponents/Footer.tsx";
import RightPanel from "./AppComponents/RightPanel.tsx";
import LeftPanel from "./AppComponents/LeftPanel.tsx";
import {useEffect, useState} from "react";
import { Simulation } from "./interfaces.ts";

function App() {
    let [simulations, setSimulations] = useState<Array<Simulation>>([])

    useEffect(() => {
        fetch('/api/simulationstates')
            .then(response => response.json())
            .then(data => setSimulations(data));
    }, []);

    return (
        <>
            <Header/>
            <div className="main-panels" id="main-panels">
                <LeftPanel/>
                <RightPanel/>
            </div>
            <Footer simulations={simulations}/>
        </>
    )
}

export default App
