package com.rev.puzzles.math.graph.algo;

import com.rev.puzzles.math.graph.Edge;
import com.rev.puzzles.math.graph.Graph;
import com.rev.puzzles.math.graph.Vertex;
import com.rev.puzzles.math.set.SetUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TravellingSalesman {

    private TravellingSalesman() {
    }

    /**
     * Implementation of HeldKarp algorithm
     * <a href="https://en.wikipedia.org/wiki/Held%E2%80%93Karp_algorithm">...</a>
     *
     * @param graph
     * @param <V>
     * @param <E>
     * @return
     */
    public static <V extends Vertex, E extends Edge> long heldKarp(final Graph<V, E> graph) {
        final ArrayList<V> vertices = new ArrayList<>(graph.getVertices());

        final Map<Pair<Set<Integer>, Integer>, Long> cache = new HashMap<>();
        final Integer[] labels = new Integer[vertices.size()];
        V first = vertices.get(0);
        labels[0] = 0;

        for (int i = 1; i < labels.length; i++) {
            labels[i] = i;
            final long weight = graph.getEdge(first, vertices.get(i)).map(E::getWeight).orElse(Long.MAX_VALUE);
            cache.put(Pair.of(Set.of(i), i), weight);
        }

        for (int i = 1; i < labels.length - 1; i++) {
            final Integer[] perms = Arrays.copyOf(labels, i + 1);
            List<Integer[]> permsOfSizeI = SetUtils.subsetsOfSizeN(labels, perms, 1);

            for (Integer[] perm : permsOfSizeI) {
                Set<Integer> mutablePermSet = new HashSet<>(List.of(perm));
                for (int k : perm) {
                    V v1 = vertices.get(k);
                    Pair<Set<Integer>, Integer> key = Pair.of(Set.of(perm), k);
                    cache.putIfAbsent(key, Long.MAX_VALUE);
                    long smallest = cache.get(key);
                    mutablePermSet.remove(k);
                    for (int m : mutablePermSet) {
                        V v2 = vertices.get(m);
                        if (graph.containsEdge(v1, v2)) {
                            final long value =
                                    cache.get(Pair.of(mutablePermSet, m))
                                            + graph.getEdge(v1, v2).get().getWeight();
                            if (value < smallest) {
                                smallest = value;
                            }
                        }
                    }
                    cache.put(key, smallest);
                    mutablePermSet.add(k);
                }
            }
        }

        final Set<Integer> setWithoutFirst = Set.of(Arrays.copyOfRange(labels, 1, labels.length));
        long result = Long.MAX_VALUE;
        for (int i = 1; i < labels.length; i++) {
            Pair<Set<Integer>, Integer> key = Pair.of(setWithoutFirst, i);
            V other = vertices.get(i);
            long weight = graph.getEdge(first, other).map(E::getWeight).orElse(Long.MAX_VALUE);
            long val = cache.get(key);
            if (val != Long.MAX_VALUE && weight != Long.MAX_VALUE) {
                result = Math.min(result, val + weight);
            }
        }

        return result;
    }

}
