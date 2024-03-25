import Map from "./Map.tsx";

function LeftPanel() {

    return (
        <>
            <div className="main-left">
                <form id="landRoverControls" className="landRoverControls">
                    <div className="coordinateInputFields">
                        <input id="roverXCoordinate" className="roverXCoordinate" placeholder="Enter X Coordinate"
                               type="number"
                               min="0" max="10"/>
                        <input id="roverYCoordinate" className="roverYCoordinate" placeholder="Enter Y Coordinate"
                               type="number"
                               min="0" max="10"/>
                    </div>
                    <button id="landRoverBtn" className="landRoverBtn">Land new Rover</button>
                </form>

                <div className="mapFlexWrapper">
                    <div id="simulationMap" className="simulationMap">
                        <Map/>
                    </div>
                </div>
            </div>
        </>
    )
}

export default LeftPanel;