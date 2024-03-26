import '../../styles/Footer.module.css'
import {useContext} from "react";
import {SimulationContext} from "../SimulationContext.tsx";

function Footer() {
    const {simulation} = useContext(SimulationContext)

    return (
        <footer id="footer">
            <div id="infoBtn" className="infoBtn"><i className="fa-solid fa-circle-info fa-2xl"></i></div>
            <div id="copyright" className="copyright"></div>
            <div id="simulationId" className="simulationId">
                <label htmlFor="simulations">Simulation ID</label>
                <select id="simulations" name="simulationList">
                    {simulation && [<option key={simulation.simulationId}>{simulation.simulationId}</option>]}
                </select>
            </div>
        </footer>
    )
}

export default Footer;