import marsSimulatorLogo from "../../assets/simulatorLogo.png";
import styles from "../../styles/Header.module.css"
import '../../styles/Header.module.css'
import {useNavigate} from "react-router";
import {useContext} from "react";
import {SimulationContext} from "../SimulationContext.tsx";

function Header() {
    const navigate = useNavigate();
    const {simulation} = useContext(SimulationContext);


    const handleGoToSimulationSelection = () => {
        navigate('/');
    };


    return (
        <div className={styles.header}>
            <svg xmlns="http://www.w3.org/2000/svg"
                 viewBox="0 0 320 512" onClick={handleGoToSimulationSelection} className={styles.backButton}>
                <path d="M9.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l192 192c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L77.3 256 246.6 86.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-192 192z"/>
            </svg>
            <div className={styles.logoWrapper} id="logoWrapper">
                <img src={marsSimulatorLogo} className={styles.applicationLogo} alt="Mars Rover Simulator logo"/>
                <h1>Mars Rover Simulator</h1>
            </div>
            <div className={styles.headerSimulationName}>Welcome to {simulation.simulationName}</div>
        </div>
    )
}

export default Header;