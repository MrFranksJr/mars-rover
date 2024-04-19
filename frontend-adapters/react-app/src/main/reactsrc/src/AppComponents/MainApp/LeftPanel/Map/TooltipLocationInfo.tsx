import {FC} from "react";
import styles from "../../../../styles/Map.module.css";
import locationArrowIcon from "../../../../assets/icons/location-arrow-solid.svg";

interface LocationProps {
    xCoordinate: number,
    yCoordinate: number,
}

const TooltipLocationInfo: FC<LocationProps> = ({xCoordinate, yCoordinate}) => {
    return (
        <>
            <div className={styles.roverStatusInfoBlock}><img src={locationArrowIcon} className={styles.locationIcon} alt="location icon"/>
                <div style={{textWrap: "nowrap"}}>{xCoordinate} – {yCoordinate}</div>
            </div>
        </>
    )
}

export default TooltipLocationInfo