export { generateMap }

function generateMap(simulationState) {
    let mapString = ""

    for(let i = simulationState.simulationSize; i>= 0; i--) {
        if(i < 10){
            mapString += "\xa0"
        }
        mapString += "<span class='mapNumber'>" +  i + "</span> <span class='mapBorder'>|</span>"
        for (let j=0; j<=simulationState.simulationSize; j++) {
            if(!simulationState.roverList.length == 0) {
                let roverSymbol = ""
                for (let rover of simulationState.roverList) {
                    if (j === rover.roverXPosition && i === rover.roverYPosition) {
                        roverSymbol = returnRoverSymbol(rover)
                    } 
                }
                mapString += roverSymbol.length > 0 ? roverSymbol : "\xa0\xB7\xa0"
            }
            else {
                mapString += "\xa0\xB7\xa0"
            }
        }
        mapString += "<br/>"
    }
    mapString += "<span class='mapBorder'>---|</span>" + "<span class='mapBorder'>-</span>".repeat((simulationState.simulationSize + 1)  * 3) + "<br/>"
    mapString += "\xa0\xa0\xa0<span class='mapBorder'>|</span>"
    for(let i = 0; i <= simulationState.simulationSize; i++){
        if (i<10) {
            mapString += "\xa0" + "<span class='mapNumber'>" + i + "</span>\xa0"
        } else {
            mapString += "\xa0" + "<span class='mapNumber'>" + i + "</span>"
        }
    }

    return mapString
}

function returnRoverSymbol(rover) {
    const roverHeading = rover.roverHeading
    const roverName = rover.roverName
    const roverStatus = rover.operationalStatus
    const roverHitPoints = rover.hitPoints
    if (roverStatus === "BROKEN") {
        return `<p class='roverSymbolWrapper'><span class='marsRover brokenRover'>\u2297${roverName}</span><span class="tooltip brokenTooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class='status-bad'>${roverStatus}</span></span></p>`
    }
    else if (roverHeading === "NORTH") {
        return `<p class='roverSymbolWrapper'><span class='marsRover'>\u2227${roverName}</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class='status-good'>${roverStatus}</span></span></p>`
    }
    else if (roverHeading === "EAST") {
        return `<p class='roverSymbolWrapper'><span class='marsRover'>${roverName}\u203A</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class='status-good'>${roverStatus}</span></span></p>`
    }
    else if (roverHeading === "SOUTH") {
        return `<p class='roverSymbolWrapper'><span class='marsRover'>${roverName}\u2228</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class='status-good'>${roverStatus}</span></span></p>`
    }
    else if (roverHeading === "WEST") {
        return `<p class='roverSymbolWrapper'><span class='marsRover'>\u2039${roverName}</span><span class="tooltip"><strong>Rover ${roverName}</strong><br/><strong>Hitpoints:</strong> ${roverHitPoints}/5<br/><strong>Status:</strong> <span class='status-good'>${roverStatus}</span></span></p>`
    }
}