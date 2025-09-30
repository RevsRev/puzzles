package com.rev.puzzles.math.seq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Ulam {

    static Ulam create(final long firstTerm, final long secondTerm) {
        if (firstTerm == 2 && secondTerm >= 5 && secondTerm % 2 == 1) {
            return new Ulam2n(secondTerm);
        }
        return new UlamAb(firstTerm, secondTerm);
    }

    long at(int n);

    final class Ulam2n implements Ulam {

        private final UlamAb ulamAb;
        private final List<Long> sequence = new ArrayList<>();

        private int hwm;
        private int evensFound = 1;
        private long[] evenTerms = new long[]{2, 0};

        public Ulam2n(final long secondTerm) {
            this.ulamAb = new UlamAb(2, secondTerm);
            hwm = 1;
            sequence.add(2L);
            sequence.add(secondTerm);
        }

        @Override
        public long at(final int n) {
            if (n <= hwm) {
                return sequence.get(n);
            }

            while (hwm < n && evensFound < 2) {
                long next = ulamAb.at(++hwm);
                sequence.add(next);
                if (next % 2 == 0) {
                    evenTerms[evensFound++] = next;
                }
            }

            extend(n);
            return sequence.get(n);
        }

        private void extend(final int n) {
            while (hwm < n) {
                final long[] candidates = new long[(int) evenTerms[1]];

                final Long last = sequence.get(sequence.size() - 1);
                for (final long evenTerm : evenTerms) {
                    for (int j = sequence.size() - 1; j >= 0 && sequence.get(j) + evenTerm > last; j--) {
                        if (sequence.get(j) == evenTerm) {
                            continue;
                        }
                        long candidate = sequence.get(j) + evenTerm;
                        int offset = (int) (candidate - (last + 1));
                        candidates[offset] = candidates[offset] + 1;
                    }
                }

                for (int i = 0; i < candidates.length; i++) {
                    if (candidates[i] == 1) {
                        sequence.add(last + 1 + i);
                        break;
                    }
                }
                hwm++;
            }
        }
    }

    final class UlamAb implements Ulam {
        private final Map<Long, Long> sieve = new HashMap<>();
        private final List<Long> sequence = new ArrayList<>();

        UlamAb(final long firstTerm, final long secondTerm) {
            sequence.add(firstTerm);
            sequence.add(secondTerm);

            sieve.put(firstTerm, 1L);
            sieve.put(secondTerm, 1L);
            sieve.put(firstTerm + secondTerm, 1L);
        }

        @Override
        public long at(final int n) {
            extend(n);

            return sequence.get(n);
        }

        //Very inefficient, could make better with some more clever caching...
        private void extend(final int n) {
            while (sequence.size() <= n) {
                long candidate = sequence.get(sequence.size() - 1) + 1;
                while (!sieve.containsKey(candidate) || sieve.get(candidate) != 1L) {
                    candidate++;
                }
                sequence.add(candidate);

                for (int i = 0; i < sequence.size() - 1; i++) {
                    final long ulam = sequence.get(i);
                    final long combinations = sieve.getOrDefault(ulam + candidate, 0L) + 1;
                    sieve.put(ulam + candidate, combinations);
                }
            }
        }
    }

}
