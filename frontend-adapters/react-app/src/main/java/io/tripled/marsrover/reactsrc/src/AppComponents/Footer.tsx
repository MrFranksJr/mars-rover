import '.././styles/Footer.css'
import {useContext} from "react";
import {SimulationContext} from "./SimulationContext.tsx";

function Footer() {
    const {simulationId} = useContext(SimulationContext)
    console.log(simulationId)

    return (
        <footer id="footer">
            <div id="infoBtn" className="infoBtn"><i className="fa-solid fa-circle-info fa-2xl"></i></div>
            <div id="copyright" className="copyright"></div>
            <div id="simulationId" className="simulationId">
                <label htmlFor="simulations">Simulation ID</label>
                <select id="simulations" name="simulationList">
                    {simulationId && [<option key={simulationId}>{simulationId}</option>]}
                </select>
            </div>
        </footer>
    )
}

export default Footer;