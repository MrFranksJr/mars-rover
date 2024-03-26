import marsSimulatorLogo from "../../assets/simulatorLogo.png";
import styles from "../../styles/Header.module.css"
import '../../styles/Header.module.css'

function Header() {
    return (
        <header>
            <div className={styles.logoWrapper} id="logoWrapper">
                <img src={marsSimulatorLogo} className={styles.applicationLogo} alt="Mars Rover Simulator logo"/>
                <h1>Mars Rover Simulator</h1>
            </div>
        </header>
    )
}

export default Header;