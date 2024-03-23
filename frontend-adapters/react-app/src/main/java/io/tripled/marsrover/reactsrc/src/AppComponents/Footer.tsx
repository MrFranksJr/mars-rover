import '.././styles/Footer.css'
import {SimulationId} from "../interfaces.ts";

function Footer({simulationId} : SimulationId) {
    return (
        <footer id="footer">
            <div id="infoBtn" className="infoBtn"><i className="fa-solid fa-circle-info fa-2xl"></i></div>
            <div id="copyright" className="copyright"></div>
            <div id="simulationId" className="simulationId">
                <label htmlFor="simulations">Simulation ID</label>
                <select id="simulations" name="simulationList">
                    <option key={simulationId}>{simulationId}</option>
                </select>
            </div>
        </footer>
    )
}

export default Footer;