import {JSX} from "react";
import styles from '../../../../styles/Map.module.css';
import {MapInteractionCSS} from 'react-map-interaction';
import Grid from "./Grid.tsx";

function Map(): JSX.Element {

    return (
        <>
            <MapInteractionCSS>
                <div className={styles.mapFlexWrapper}>
                    <Grid />
                </div>
            </MapInteractionCSS>
        </>
    )
}

export default Map
