package io.tripled.marsrover.vocabulary;

public record RoverId(String id) {

    public RoverId(int id) {
        this("R" + id);
    }
}
