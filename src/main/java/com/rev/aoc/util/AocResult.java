package com.rev.aoc.util;

import com.rev.aoc.AocCoordinate;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
public final class AocResult {
    private final AocCoordinate coordinate;
    private final Optional<Long> partOne;
    private final Optional<Long> partTwo;
    private final Optional<Throwable> error;

    private AocResult(final AocCoordinate coordinate, final Optional<Long> partOne, final Optional<Long> partTwo) {
        this.coordinate = coordinate;
        this.partOne = partOne;
        this.partTwo = partTwo;
        this.error = Optional.empty();
    }

    private AocResult(final AocCoordinate coordinate, final Throwable error) {
        this.coordinate = coordinate;
        this.error = Optional.ofNullable(error);
        this.partOne = Optional.empty();
        this.partTwo = Optional.empty();
    }

    public static AocResult error(final AocCoordinate coordinate, final Throwable error) {
        return new AocResult(coordinate, error);
    }

    @Setter @Getter
    public static final class Builder {
        private AocCoordinate coordinate;
        private Long partOne = null;
        private Long partTwo = null;

        public AocResult build() throws UninitializedException {
            if (coordinate == null) {
                throw new UninitializedException("Year and day was not specified when building solution");
            }
            return new AocResult(coordinate, Optional.ofNullable(partOne), Optional.ofNullable(partTwo));
        }

    }

    public static final class UninitializedException extends Exception {
        public UninitializedException(final String message) {
            super(message);
        }
    }
}
