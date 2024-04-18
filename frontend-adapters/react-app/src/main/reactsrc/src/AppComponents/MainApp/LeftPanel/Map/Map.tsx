import {JSX} from "react";
//import {SimulationContext} from "../../../SimulationContext.tsx";
import marsMap1 from '../../../../assets/map/marsMap1.jpeg';
import marsMap2 from '../../../../assets/map/marsMap2.jpeg';
import marsMap3 from '../../../../assets/map/marsMap3.jpeg';
import marsMap4 from '../../../../assets/map/marsMap4.jpeg';
import marsMap5 from '../../../../assets/map/marsMap5.jpeg';
import marsMap6 from '../../../../assets/map/marsMap6.jpeg';
import marsMap7 from '../../../../assets/map/marsMap7.jpeg';
import '../../../../styles/Map.css';
import {MapInteractionCSS} from 'react-map-interaction';

function Map(): JSX.Element {
    //const {simulation} = useContext(SimulationContext);
    const backgrounds = [
        marsMap1,
        marsMap2,
        marsMap3,
        marsMap4,
        marsMap5,
        marsMap6,
        marsMap7
    ]

    const generateRandomNumber = (): number => {
        return Math.floor(Math.random() * 7) + 1; // Generates a random number between 1 and 7
    };

    const randomBackgroundPicker = () => {
        return backgrounds[generateRandomNumber()]
    }

    return (
        <>
            <MapInteractionCSS>
                <div className="mapFlexWrapper">
                    <img src={randomBackgroundPicker()} alt="background for the map"/>
                </div>
            </MapInteractionCSS>
        </>
    )
}

export default Map
