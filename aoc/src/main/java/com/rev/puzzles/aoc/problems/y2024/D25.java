package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class D25 {

    @AocProblemI(year = 2024, day = 25, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
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
    public String partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        return "n/a";
    }

    private void loadKeysAndLocks(final ProblemResourceLoader<List<String>> resourceLoader, final Set<KeyOrLock> keys,
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

    private record KeyOrLock(int[] pins) {
        private static final int PIN_DEPTH = 7;

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
