package com.rev.aoc;

public class AocEngine implements Runnable {
    private final AocCoordinate firstAocCoordinate;
    private final AocCoordinate secondAocCoordinate;

    public AocEngine(final AocCoordinate firstAocCoordinate,
                     final AocCoordinate secondAocCoordinate) {
        this.firstAocCoordinate = firstAocCoordinate;
        this.secondAocCoordinate = secondAocCoordinate;
    }


    @Override
    public void run() {

    }
}
