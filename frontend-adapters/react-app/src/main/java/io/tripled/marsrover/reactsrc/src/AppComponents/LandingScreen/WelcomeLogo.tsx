import marsSimulatorLogo from "../../assets/simulatorLogo.png";

function WelcomeLogo() {
    return (
        <div className="logoWrapper" id="logoWrapper">
            <img src={marsSimulatorLogo} className="applicationLogo" alt="Mars Rover Simulator logo"/>
            <h1>Welcome</h1>
        </div>
    )
}

export { WelcomeLogo }