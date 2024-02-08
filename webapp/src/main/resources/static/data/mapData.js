export { generateMap }

function generateMap(simulationState) {
    let mapString = "";

    for(let i=simulationState.simulationSize; i>= 0; i--) {
        if(i < 10){
            mapString += "\xa0";
        }
        mapString += "<span class='mapNumber'>" +  i + "</span> <span class='mapBorder'>|</span>";
        for (let j=0; j<=simulationState.simulationSize; j++) {
            if(!simulationState.roverList.length == 0 && j == simulationState.roverList[0].roverXPosition && i == simulationState.roverList[0].roverYPosition) {
                mapString += "<span class='marsRover'>" + returnRoverSymbol(simulationState) + "</span>"
            } else {
                mapString += "\xa0\xB7\xa0";
            }
        }
        mapString += "<br/>";
    }

    mapString += "\xa0\xa0\xa0<span class='mapBorder'>|</span>" + "<span class='mapBorder'>-</span>".repeat((simulationState.simulationSize + 1)  * 3) + "<br/>";
    mapString += "\xa0\xa0\xa0<span class='mapBorder'>|</span>";

    for(let i = 0; i <= simulationState.simulationSize; i++){
        if (i<10) {
            mapString += "\xa0" + "<span class='mapNumber'>" + i + "</span>\xa0";
        } else {
            mapString += "\xa0" + "<span class='mapNumber'>" + i + "</span>";
        }
    }

    return mapString;
}

function returnRoverSymbol(simulationState) {
    const roverHeading = simulationState.roverList[0].roverHeading
    const roverName = simulationState.roverList[0].roverName
    if (roverHeading == "NORTH") {
        return "\u2227" + roverName
    }
    else if (roverHeading == "EAST") {
        return roverName + "\u203A"
    }
    else if (roverHeading == "SOUTH") {
        return roverName + "\u2228"
    }
    else if (roverHeading == "WEST") {
        return "\u2039" + roverName
    }
}