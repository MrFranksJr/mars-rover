import {FC} from "react";
import marsRover from "../../../../assets/marsRoverGraphic.png";
import styles from '../../../../styles/Map.module.css'

interface RoverGraphicProps {
    heading: string
    roverName: string
}

const MarsRoverGraphic: FC<RoverGraphicProps> = ({heading, roverName}) => {
    const calulateRotation = () => {
        if (heading === "NORTH") {
            return "rotate(0deg)";
        }
        if (heading === "EAST") {
            return "rotate(90deg)";
        }
        if (heading === "SOUTH") {
            return "rotate(180deg)";
        }
        if (heading === "WEST") {
            return "rotate(270deg)";
        }
    }

    return (
        <>
            <div className={styles.roverGraphicContainer}>
                <img src={marsRover} alt="Mars Rover icon" className={styles.roverGraphic} style={{width: "70%", transform: calulateRotation(), transition:"0.2s ease-in-out"}}/>
                <div className={styles.roverGraphicsRoverName}>{roverName}</div>
            </div>
        </>
    )
}

export default MarsRoverGraphic