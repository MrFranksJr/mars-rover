import {FC, useEffect, useState} from "react";
import styles from '../../../../styles/Map.module.css'
// Define image paths
const roverImagePaths = [
    "../../../../assets/map/marsRoverGraphic1.png",
    "../../../../assets/map/marsRoverGraphic2.png",
    "../../../../assets/map/marsRoverGraphic3.png",
    "../../../../assets/map/marsRoverGraphic4.png",
    "../../../../assets/map/marsRoverGraphic5.png",
    "../../../../assets/map/marsRoverGraphic6.png",
    "../../../../assets/map/marsRoverGraphic7.png",
    "../../../../assets/map/marsRoverGraphic8.png",
];


interface RoverGraphicProps {
    heading: string
    roverName: string
}

const MarsRoverGraphic: FC<RoverGraphicProps> = ({heading, roverName}) => {
    const [randomImagePath, setRandomImagePath] = useState<string>("");

    useEffect(() => {
        const generateRandomImagePath = async () => {
            const randomIndex = Math.floor(Math.random() * roverImagePaths.length);
            const imagePath = roverImagePaths[randomIndex];
            const imageModule = await import(imagePath);
            setRandomImagePath(imageModule.default);
        };
        generateRandomImagePath();
    }, []);




    const caluclateRotation = () => {
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
                <img src={randomImagePath} alt="Mars Rover icon" className={styles.roverGraphic} style={{width: "70%", transform: caluclateRotation(), transition:"0.2s ease-in-out"}}/>
                <div className={styles.roverGraphicsRoverName}>{roverName}</div>
            </div>
        </>
    )
}

export default MarsRoverGraphic