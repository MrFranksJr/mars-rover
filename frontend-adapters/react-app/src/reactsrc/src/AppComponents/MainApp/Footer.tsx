import styles from '../../styles/Footer.module.css'
import {useContext} from "react";
import {SimulationContext} from "../SimulationContext.tsx";
import tripleDLogo from "../../assets/TripleD.svg"

function Footer() {
    const {simulation} = useContext(SimulationContext)

    return (
        <footer id="footer">
            <div id="infoBtn" className={styles.infoBtn}><i className="fa-solid fa-circle-info fa-2xl"></i></div>
            <div id="copyright" className={styles.copyright}>
                {'\xA9'}{new Date().getFullYear()}{'\xa0'}<img src={tripleDLogo} className={styles.tripledLogo} alt="triple d logo"/> Mars Rover Association
            </div>
            <div id="simulationId" className={styles.simulationId}>
                <div>Simulation ID</div>
                <div>{simulation.simulationId}</div>
            </div>
        </footer>
    )
}

export default Footer;