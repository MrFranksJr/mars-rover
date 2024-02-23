package io.tripled.marsrover.dto;

import java.util.List;

/**
 * Part of the public API that is used by the Frontend
 *
 * @param simulationSize
 * @param totalCoordinates
 * @param roverList
 */
public record SimulationViewDTO(int simulationSize, int totalCoordinates, List<RoverViewDTO> roverList) {
}