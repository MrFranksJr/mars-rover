import marsSimulatorLogo from "../../assets/simulatorLogo.png";
import styles from '../../styles/SimulationSelectionOptions.module.css'


function WelcomeLogo() {
    return (
        <div className={styles.logoWrapper} id="logoWrapper">
            <img src={marsSimulatorLogo} className="applicationLogo" alt="Mars Rover Simulator logo"/>
            <h1>Welcome</h1>
        </div>
    )
}

export { WelcomeLogo }