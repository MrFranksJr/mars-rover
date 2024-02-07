export { generateMap }

function generateMap(simulationState) {
    console.log('drawing map')
    console.log(simulationState)
    let mapArray;
    for(let i=0; i<=simulationState.simulationSize; i++) {
        for (let j=0; j<=simulationState.simulationSize; j++) {
            mapArray[i][j] = ".";
        }
    }
    console.log(mapArray);
    return mapArray
}