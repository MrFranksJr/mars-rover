import styles from "../../../styles/LandingControls.module.css"
function landingControls() {
    return (
        <>
            <form id="landRoverControls" className={styles.landRoverControls}>
                <div className={styles.coordinateInputFields}>
                    <input id="roverXCoordinate" className={styles.roverXCoordinate} placeholder="Enter X Coordinate"
                           type="number"
                           min="0" max="10"/>
                    <input id="roverYCoordinate" className={styles.roverYCoordinate} placeholder="Enter Y Coordinate"
                           type="number"
                           min="0" max="10"/>
                </div>
                <button id="landRoverBtn" className={styles.landRoverBtn}>Land new Rover</button>
            </form>
        </>
    )
}

export default landingControls