import SimulationStateTable from "./SimulationStateTable.tsx";
import styles from "../../../styles/RightPanel.module.css"
import RoverControls from "./RoverControls.tsx";
function RightPanel() {
    return (
        <>
            <div className={styles.mainRight}>
                <RoverControls />
                <SimulationStateTable/>
            </div>
        </>
    )
}

export default RightPanel;