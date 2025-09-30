package com.rev.puzzles.math.seq;

import java.util.ArrayList;
import java.util.List;

public final class Ulam {

    private final List<Long> sequence = new ArrayList<>();

    public Ulam(final long firstTerm, final long secondTerm) {
        sequence.add(firstTerm);
        sequence.add(secondTerm);
    }

    public long at(final int n) {
        extend(n);

        return sequence.get(n);
    }

    //Very inefficient, could make better with some more clever caching...
    private void extend(final int n) {
        long candidate = sequence.get(sequence.size() - 1) + 1;
        while (sequence.size() <= n) {
            int count = 0;
            for (int i = 0; i < sequence.size(); i++) {
                for (int j = i + 1; j < sequence.size(); j++) {
                    if (candidate == sequence.get(i) + sequence.get(j)) {
                        count++;
                    }
                }
            }
            if (count == 1) {
                sequence.add(candidate);
            }
            candidate = candidate + 1;
        }
    }

}
