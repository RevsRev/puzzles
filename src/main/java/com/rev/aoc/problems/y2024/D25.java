package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class D25 extends AocProblem<Long, String> {
    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 25);
    }

    @AocProblemI(year = 2024, day = 25, part = 1)
    @Override
    protected Long partOneImpl(final ResourceLoader resourceLoader) {
        Set<KeyOrLock> keys = new HashSet<>();
        Set<KeyOrLock> locks = new HashSet<>();
        loadKeysAndLocks(resourceLoader, keys, locks);

        long count = 0;
        for (KeyOrLock key : keys) {
            for (KeyOrLock lock : locks) {
                if (lock.accepts(key)) {
                    count++;
                }
            }
        }
        return count;
    }

    @AocProblemI(year = 2024, day = 25, part = 2)
    @Override
    protected String partTwoImpl(final ResourceLoader resourceLoader) {
        return "n/a";
    }

    private void loadKeysAndLocks(
            final ResourceLoader resourceLoader,
            final Set<KeyOrLock> keys,
            final Set<KeyOrLock> locks) {
        List<String> lines = resourceLoader.resources();
        int start = 0;
        while (start < lines.size()) {
            while (start < lines.size() && lines.get(start).isBlank()) {
                start++;
            }
            if (start == lines.size()) {
                break;
            }

            int[] keyOrLock = new int[]{0, 0, 0, 0, 0};
            boolean lock = false;
            for (int i = start; i < start + 7; i++) {
                if (i == start && lines.get(start).equals("#####")) {
                    lock = true;
                }
                for (int j = 0; j < 5; j++) {
                    if (lines.get(i).charAt(j) == '#') {
                        keyOrLock[j] = keyOrLock[j] + 1;
                    }
                }
            }
            if (lock) {
                locks.add(new KeyOrLock(keyOrLock));
            } else {
                keys.add(new KeyOrLock(keyOrLock));
            }
            start += 7;
        }
    }

    private static final class KeyOrLock {
        private static final int PIN_DEPTH = 7;
        private final int[] pins;

        private KeyOrLock(final int[] pins) {
            this.pins = pins;
        }

        public boolean accepts(final KeyOrLock other) {
            for (int i = 0; i < pins.length; i++) {
                if (pins[i] + other.pins[i] > PIN_DEPTH) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            KeyOrLock keyOrLock = (KeyOrLock) o;
            return Arrays.equals(pins, keyOrLock.pins);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(pins);
        }
    }
}
