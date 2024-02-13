package io.tripled.marsrover.vocabulary;

import com.google.common.collect.ImmutableList;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.vocabulary.RoverId;

public record RoverInstructions(RoverId id, ImmutableList<RoverMove> move){}
