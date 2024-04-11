import Map from "./Map/Map.tsx";
import styles from "../../../styles/LeftPanel.module.css"
import LandingControls from "./LandingControls.tsx";

function LeftPanel() {

    return (
        <>
            <div className={styles.mainLeft}>
                <LandingControls/>
                <Map/>
            </div>
        </>
    )
}

export default LeftPanel;