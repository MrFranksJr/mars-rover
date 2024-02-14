package io.tripled.marsrover.vocabulary;

import static java.lang.Integer.parseInt;

public record RoverId(String id) {
    public RoverId(int idNR) {
        this("R" + idNR);
    }

    public RoverId {
        if (!id.startsWith("R"))
            throw new IllegalArgumentException("A rover id starts with R");
        final String substring = id.substring(1);
        try {
            parseInt(substring);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("A rover id starts with R followed by numbers");
        }
    }

    @Override
    public String toString() {
        return "[" + id + "]";
    }
}
