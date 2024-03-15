export { createSimulation }

async function createSimulation() {
    const result = await fetch(`/api/createsimulation/10`, {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
    let data = await result.json();
    return data.simulationId
}