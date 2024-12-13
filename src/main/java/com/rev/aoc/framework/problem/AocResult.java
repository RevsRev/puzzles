package com.rev.aoc.framework.problem;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
public final class AocResult {
    private final AocCoordinate coordinate;
    private final Optional<Long> partOne;
    private final Optional<Long> partOneTime;
    private final Optional<Long> partTwo;
    private final Optional<Long> partTwoTime;
    private final Optional<Throwable> error;

    private AocResult(final AocCoordinate coordinate,
                      final Optional<Long> partOne,
                      final Optional<Long> partOneTime,
                      final Optional<Long> partTwo,
                      final Optional<Long> partTwoTime) {
        this.coordinate = coordinate;
        this.partOne = partOne;
        this.partOneTime = partOneTime;
        this.partTwo = partTwo;
        this.partTwoTime = partTwoTime;
        this.error = Optional.empty();
    }

    private AocResult(final AocCoordinate coordinate, final Throwable error) {
        this.coordinate = coordinate;
        this.error = Optional.ofNullable(error);
        this.partOne = Optional.empty();
        this.partOneTime = Optional.empty();
        this.partTwo = Optional.empty();
        this.partTwoTime = Optional.empty();
    }

    public static AocResult error(final AocCoordinate coordinate, final Throwable error) {
        return new AocResult(coordinate, error);
    }

    @Setter @Getter
    public static final class Builder {
        private AocCoordinate coordinate;
        private Long partOne = null;
        private Long partOneTime = null;
        private Long partTwo = null;
        private Long partTwoTime = null;

        public AocResult build() throws UninitializedException {
            if (coordinate == null) {
                throw new UninitializedException("Year and day was not specified when building solution");
            }
            return new AocResult(coordinate,
                    Optional.ofNullable(partOne),
                    Optional.ofNullable(partOneTime),
                    Optional.ofNullable(partTwo),
                    Optional.ofNullable(partTwoTime));
        }

    }

    public static final class UninitializedException extends Exception {
        public UninitializedException(final String message) {
            super(message);
        }
    }
}
