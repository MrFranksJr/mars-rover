import {JSX, useContext} from "react";
import {SimulationContext} from "../../../SimulationContext.tsx";
import '../../../../styles/Map.css';
import {MapInteractionCSS} from 'react-map-interaction';
import Grid from "./Grid.tsx";

function Map(): JSX.Element {

    return (
        <>
            <MapInteractionCSS>
                <div className="mapFlexWrapper">
                    <Grid />
                </div>
            </MapInteractionCSS>
        </>
    )
}

export default Map
