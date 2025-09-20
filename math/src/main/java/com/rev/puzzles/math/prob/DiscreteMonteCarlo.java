package com.rev.puzzles.math.prob;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class DiscreteMonteCarlo<T> {

    private final Distribution<T> distribution;
    private final Collection<T> values;
    private final int minSamples;
    private final double maxAbsError;

    private long numSamples = 0;
    private final Map<T, Integer> samples = new HashMap<>();
    private final Map<T, Double> probabilities = new HashMap<>();

    //TODO - This is a noddy implementation. Really we should be making epsilon (maxAbsError) a function of numSamples?
    public DiscreteMonteCarlo(final Distribution<T> distribution, final Collection<T> values, final int minSamples,
                              final double maxAbsError) {
        this.distribution = distribution;
        this.values = values;
        this.minSamples = minSamples;
        this.maxAbsError = maxAbsError;
    }

    public Map<T, Double> run() {
        if (numSamples != 0) {
            return new HashMap<>(probabilities);
        }

        values.forEach(v -> {
            samples.put(v, 0);
            probabilities.put(v, 0d);
        });

        Map<T, Double> previousProbabilities;
        do {
            final T sample = distribution.sample();
            previousProbabilities = new HashMap<>(probabilities);
            samples.put(sample, samples.get(sample) + 1);
            numSamples++;
            samples.forEach((s, c) -> probabilities.put(s, c / (double) numSamples));
        } while (numSamples < minSamples || !absErrorSufficientlySmall(previousProbabilities));

        return new HashMap<>(probabilities);
    }

    private boolean absErrorSufficientlySmall(final Map<T, Double> previousProbabilities) {
        double absError = 0;
        for (final Map.Entry<T, Double> sampleAndProb : probabilities.entrySet()) {
            absError = Math.max(absError, sampleAndProb.getValue() - previousProbabilities.get(sampleAndProb.getKey()));
        }
        return absError < maxAbsError;
    }
}
