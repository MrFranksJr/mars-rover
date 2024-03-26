import {JSX, useContext} from "react";
import {SimulationContext} from "../../SimulationContext.tsx";
import {Rover} from "../../../interfaces.ts";
import DOMPurify from "dompurify";
import parse from 'html-react-parser'
import '../../../styles/Map.css'

function Map(): JSX.Element {
    const {simulation} = useContext(SimulationContext);

    function generateMap(): string {
        let mapString = ""

        for (let i = simulation.simulationSize; i >= 0; i--) {
            if (i < 10) {
                mapString += "\xa0"
            }
            mapString += "<span class='mapNumber'>" + i + "</span> <span class='mapBorder'>|</span>"
            for (let j = 0; j <= simulation.simulationSize; j++) {
                if (simulation.roverList.length !== 0) {
                    let roverSymbol = ""
                    for (let rover of simulation.roverList) {
                        if (j === rover.roverXPosition && i === rover.roverYPosition) {
                            roverSymbol = returnRoverSymbol(rover)
                        }
                    }
                    mapString += roverSymbol.length > 0 ? roverSymbol : "\xa0\xB7\xa0"
                } else {
                    mapString += "\xa0\xB7\xa0"
                }
            }
            mapString += "<br/>"
        }
        mapString += "<span class='mapBorder'>---|</span>" + "<span class='mapBorder'>-</span>".repeat((simulation.simulationSize + 1) * 3) + "<br/>"
        mapString += "\xa0\xa0\xa0<span class='mapBorder'>|</span>"
        for (let i = 0; i <= simulation?.simulationSize; i++) {
            if (i < 10) {
                mapString += "\xa0" + "<span class='mapNumber'>" + i + "</span>\xa0"
            } else {
                mapString += "\xa0" + "<span class='mapNumber'>" + i + "</span>"
            }
        }

        return mapString
    }

    function returnRoverSymbol(rover: Rover):string {
        const roverHeading = rover.roverHeading
        const roverName = rover.roverName
        const roverStatus = rover.operationalStatus
        const roverHitPoints = rover.hitPoints
        if (roverStatus === "BROKEN") {
            return `<p class="roverSymbolWrapper"><span class='roverSymbolWrapper'>\u2297${roverName}</span><span class="tooltip brokenTooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class="statusBad">${roverStatus}</span></span></p>`
        } else if (roverHeading === "NORTH") {
            return `<p class="roverSymbolWrapper"><span class='roverSymbolWrapper'>\u2227${roverName}</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class="statusGood">${roverStatus}</span></span></p>`
        } else if (roverHeading === "EAST") {
            return `<p class="roverSymbolWrapper"><span class='roverSymbolWrapper'>${roverName}\u203A</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class="statusGood">${roverStatus}</span></span></p>`
        } else if (roverHeading === "SOUTH") {
            return `<p class="roverSymbolWrapper"><span class='roverSymbolWrapper'>${roverName}\u2228</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class="statusGood">${roverStatus}</span></span></p>`
        } else if (roverHeading === "WEST") {
            return `<p class="roverSymbolWrapper"><span class='roverSymbolWrapper'>\u2039${roverName}</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class="statusGood">${roverStatus}</span></span></p>`
        }
        return "";
    }

    const sanitizedData = DOMPurify.sanitize(generateMap())

    return (
        <>
            <div className="mapFlexWrapper">
                <div id="simulationMap" className="simulationMap">
                    <div className="mapInnerDiv">{parse(sanitizedData)}</div>
                </div>
            </div>
        </>
    )
}

export default Map
