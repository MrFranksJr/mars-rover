import {JSX} from "react";
import styles from '../../../../styles/Map.module.css';
import {MapInteractionCSS} from 'react-map-interaction';
import Grid from "./Grid.tsx";

function Map(): JSX.Element {

    return (
        <>
            <div className={styles.interactiveMapWrapper}>
                <MapInteractionCSS>
                    <div className={styles.mapFlexWrapper}>
                        <Grid />
                    </div>
                </MapInteractionCSS>
            </div>
        </>
    )
}

export default Map
