package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.RoverMove;

public record ExtrapolatedInstruction(RoverId roverId, RoverMove roverMove) {}
