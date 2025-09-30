package com.rev.puzzles.math.seq;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Ulam {

    private final List<BigInteger> sequence = new ArrayList<>();

    public Ulam(final BigInteger firstTerm, final BigInteger secondTerm) {
        sequence.add(firstTerm);
        sequence.add(secondTerm);
    }

    public BigInteger at(final int n) {
        extend(n);

        return sequence.get(n);
    }

    //Very inefficient, could make better with some more clever caching...
    private void extend(final int n) {
        BigInteger candidate = sequence.get(sequence.size() - 1).add(BigInteger.ONE);
        while (sequence.size() <= n) {
            int count = 0;
            for (int i = 0; i < sequence.size(); i++) {
                for (int j = i + 1; j < sequence.size(); j++) {
                    if (candidate.equals(sequence.get(i).add(sequence.get(j)))) {
                        count++;
                    }
                }
            }
            if (count == 1) {
                sequence.add(candidate);
            }
            candidate = candidate.add(BigInteger.ONE);
        }
    }

}
