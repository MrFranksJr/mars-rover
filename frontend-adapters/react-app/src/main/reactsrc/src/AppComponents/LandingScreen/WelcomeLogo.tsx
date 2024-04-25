import marsSimulatorLogo from "../../assets/simulatorLogo.png";
import styles from '../../styles/SimulationSelectionOptions.module.css'
import {FormStateProps} from "../../interfaces.ts";


function WelcomeLogo({formState}: FormStateProps) {
    return (
        <div className={styles.logoWrapper} id="logoWrapper">
            <img src={marsSimulatorLogo} className={styles.applicationLogo} alt="Mars Rover Simulator logo"/>
            {formState === "INIT" && (
                <h1 className={styles.h1}>Welcome</h1>
            )}
            {formState === "CREATE" && (
                <h1 className={styles.h1}>Create new Simulation</h1>
            )}
        </div>
    )
}

export {WelcomeLogo}