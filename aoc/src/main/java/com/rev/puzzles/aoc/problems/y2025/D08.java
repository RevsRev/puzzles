package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.math.linalg.vec.Vec3;
import org.jgrapht.alg.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class D08 {

    @AocProblemI(year = 2025, day = 8, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {

        final List<Vec3> positions = resourceLoader.resources().stream().map(s -> {
            final String[] split = s.split(",");
            return new Vec3(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
        }).toList();

        final TreeMap<Double, Set<Pair<Vec3, Vec3>>> distances = getSortedDistances(positions);

        final Map<Vec3, Set<Vec3>> connections = new HashMap<>();
        int numPairsConsidered = 0;
        final int maxNumPairs = 1000;

        for (final Map.Entry<Double, Set<Pair<Vec3, Vec3>>> connectionCandidates : distances.entrySet()) {
            for (final Pair<Vec3, Vec3> connectionCandidate : connectionCandidates.getValue()) {
                final Vec3 first = connectionCandidate.getFirst();
                final Vec3 second = connectionCandidate.getSecond();

                final Set<Vec3> firstConnection = connections.get(first);
                final Set<Vec3> secondConnection = connections.get(second);

                numPairsConsidered++;
                if (firstConnection == null && secondConnection == null) {
                    final HashSet<Vec3> circuit = new HashSet<>();
                    circuit.add(first);
                    circuit.add(second);
                    connections.put(first, circuit);
                    connections.put(second, circuit);
                } else if (firstConnection == null) {
                    secondConnection.add(first);
                    connections.put(first, secondConnection);
                } else if (secondConnection == null) {
                    firstConnection.add(second);
                    connections.put(second, firstConnection);
                } else if (!firstConnection.equals(secondConnection)) {
                    final HashSet<Vec3> replaced = new HashSet<>(firstConnection);
                    replaced.addAll(secondConnection);
                    replaced.forEach(v -> connections.put(v, replaced));
                }

                if (numPairsConsidered == maxNumPairs) {
                    break;
                }
            }
            if (numPairsConsidered == maxNumPairs) {
                break;
            }
        }

        final List<Set<Vec3>> circuits = new ArrayList<>(new HashSet<>(connections.values()));
        circuits.sort(Comparator.comparingInt(Set::size));

        final int limit = 3;
        long product = 1;
        for (int i = circuits.size() - 1; i >= circuits.size() - limit; i--) {
            product *= circuits.get(i).size();
        }

        return product;
    }

    @AocProblemI(year = 2025, day = 8, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<Vec3> positions = resourceLoader.resources().stream().map(s -> {
            final String[] split = s.split(",");
            return new Vec3(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
        }).toList();

        final TreeMap<Double, Set<Pair<Vec3, Vec3>>> distances = getSortedDistances(positions);

        final Map<Vec3, Set<Vec3>> connections = new HashMap<>();

        for (final Map.Entry<Double, Set<Pair<Vec3, Vec3>>> connectionCandidates : distances.entrySet()) {
            for (final Pair<Vec3, Vec3> connectionCandidate : connectionCandidates.getValue()) {
                final Vec3 first = connectionCandidate.getFirst();
                final Vec3 second = connectionCandidate.getSecond();

                final Set<Vec3> firstConnection = connections.get(first);
                final Set<Vec3> secondConnection = connections.get(second);

                if (firstConnection == null && secondConnection == null) {
                    final HashSet<Vec3> circuit = new HashSet<>();
                    circuit.add(first);
                    circuit.add(second);
                    connections.put(first, circuit);
                    connections.put(second, circuit);
                } else if (firstConnection == null) {
                    secondConnection.add(first);
                    connections.put(first, secondConnection);
                } else if (secondConnection == null) {
                    firstConnection.add(second);
                    connections.put(second, firstConnection);
                } else if (!firstConnection.equals(secondConnection)) {
                    final HashSet<Vec3> replaced = new HashSet<>(firstConnection);
                    replaced.addAll(secondConnection);
                    replaced.forEach(v -> connections.put(v, replaced));
                }

                if (connections.get(first).size() == positions.size()) {
                    return (long) first.getX() * (long) second.getX();
                }
            }
        }

        throw new ProblemExecutionException("Algorithm did not terminate correctly");
    }

    private static TreeMap<Double, Set<Pair<Vec3, Vec3>>> getSortedDistances(final List<Vec3> positions) {
        final TreeMap<Double, Set<Pair<Vec3, Vec3>>> distances = new TreeMap<>();

        for (int i = 0; i < positions.size(); i++) {
            final Vec3 first = positions.get(i);
            for (int j = i + 1; j < positions.size(); j++) {
                final Vec3 second = positions.get(j);
                final Vec3 difference = Vec3.minus(first, second);
                final double distance = difference.abs();
                distances.computeIfAbsent(distance, k -> new HashSet<>()).add(Pair.of(first, second));
            }
        }
        return distances;
    }
}
