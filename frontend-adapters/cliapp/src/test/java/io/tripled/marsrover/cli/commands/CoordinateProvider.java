package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.vocabulary.Coordinate;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

class CoordinateProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(new Coordinate(0, 0)),
                Arguments.of(new Coordinate(1, 0)),
                Arguments.of(new Coordinate(0, 1)),
                Arguments.of(new Coordinate(7, 8)),
                Arguments.of(new Coordinate(4, 6))
        );
    }
}
