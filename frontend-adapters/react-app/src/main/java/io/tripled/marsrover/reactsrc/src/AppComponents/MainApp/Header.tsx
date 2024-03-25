import marsSimulatorLogo from "../../assets/simulatorLogo.png";
import '../../styles/Header.css'

function Header() {
    return (
        <header>
            <div className="logoWrapper" id="logoWrapper">
                <img src={marsSimulatorLogo} className="applicationLogo" alt="Mars Rover Simulator logo"/>
                <h1>Mars Rover Simulator</h1>
            </div>
        </header>
    )
}

export default Header;