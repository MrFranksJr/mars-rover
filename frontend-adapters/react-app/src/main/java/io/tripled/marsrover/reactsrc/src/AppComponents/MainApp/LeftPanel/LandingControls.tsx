import landingControlsStyles from "../../../styles/LandingControls.module.css"
import appStyles from "../../../styles/App.module.css"
function landingControls() {
    return (
        <>
            <form id="landRoverControls" className={landingControlsStyles.landRoverControls}>
                <div className={landingControlsStyles.coordinateInputFields}>
                    <input id="roverXCoordinate" className={landingControlsStyles.roverXCoordinate} placeholder="Enter X Coordinate"
                           type="number"
                           min="0" max="10"/>
                    <input id="roverYCoordinate" className={landingControlsStyles.roverYCoordinate} placeholder="Enter Y Coordinate"
                           type="number"
                           min="0" max="10"/>
                </div>
                <button id="landRoverBtn" className={appStyles.landRoverBtn}>Land new Rover</button>
            </form>
        </>
    )
}

export default landingControls