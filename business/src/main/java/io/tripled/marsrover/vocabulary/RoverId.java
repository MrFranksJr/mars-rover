package io.tripled.marsrover.vocabulary;


import java.util.Comparator;

import static java.lang.Integer.parseInt;

public record RoverId(String id) {
    public static final Comparator<RoverId> COMPARATOR = (o1, o2) -> {
        if (o1.equals(o2)) {
            return 0;
        } else if (o1.isSmaller(o2))
            return -1;
        else
            return 1;
    };
    public boolean isSmaller(RoverId other) {
        final int thisRoverId = parseInt(this.id.substring(1));
        final int otherRoverId = parseInt(other.id.substring(1));
        return thisRoverId < otherRoverId;
    }

    public boolean isBigger(RoverId roverId) {
        return !isSmaller(roverId);
    }

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

    public int compareWith(RoverId secondRoverId) {
        return COMPARATOR.compare(this, secondRoverId);
    }

    @Override
    public String toString() {
        return "[" + id + "]";
    }

}
