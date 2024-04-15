package io.tripled.marsrover.dto;

import java.util.List;

/**
 * Part of the public API that is used by the Frontend
 *
 * @param simulationId
 * @param simulationName
 * @param simulationSize
 * @param totalCoordinates
 * @param roverList
 */
public record SimulationViewDTO(String simulationId, String simulationName, int simulationSize, int totalCoordinates, List<RoverViewDTO> roverList) {
}