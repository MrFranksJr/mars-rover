import landingControlsStyles from "../../../styles/LandingControls.module.css"
import appStyles from "../../../styles/App.module.css"
import {useState} from "react";
function landingControls() {
    const [formData, setFormData] = useState({
        xCoordinate: "",
        yCoordinate: "",
    });

    const handleChange = (event:any) => {
        const { id, value } = event.target;
        setFormData((prevState) => ({ ...prevState, [id]: value }));
    };


    return (
        <>
            <form id="landRoverControls" className={landingControlsStyles.landRoverControls} onSubmit={e => {
                e.preventDefault();
                alert('Submitting!');
            }}>
                <div className={landingControlsStyles.coordinateInputFields}>
                    <input id="roverXCoordinate"
                           className={landingControlsStyles.roverXCoordinate}
                           placeholder="Enter X Coordinate"
                           type="number"
                           min="0" max="10"
                           value={formData.xCoordinate}
                           onChange={handleChange}
                    />
                    <input id="roverYCoordinate"
                           className={landingControlsStyles.roverYCoordinate}
                           placeholder="Enter Y Coordinate"
                           type="number"
                           min="0" max="10"
                           value={formData.yCoordinate}
                           onChange={handleChange}
                    />
                </div>
                <button id="landRoverBtn" className={appStyles.landRoverBtn}>Land new Rover</button>
            </form>
        </>
    )
}

export default landingControls