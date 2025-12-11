package com.rev.puzzles.math.graph.algo;

import com.rev.puzzles.math.graph.Edge;
import com.rev.puzzles.math.graph.Graph;
import com.rev.puzzles.math.graph.Vertex;
import com.rev.puzzles.math.set.SetUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public final class Cliques {

    private Cliques() {
    }

    public static <V extends Vertex, E extends Edge> Collection<Graph<V, E>> getCliques(
            final Graph<V, E> graph) {
        Graph.Builder<V, E> clique = new Graph.Builder<>(graph.getVertexCreator(), graph.getEdgeCreator(), false);
        Set<V> remaining = new HashSet<>(graph.getVertices());
        Set<V> considered = new HashSet<>();

        final Set<Graph<V, E>> cliques = new HashSet<>();
        Consumer<Graph<V, E>> consumer = cliques::add;
        getCliques(graph, clique, remaining, considered, consumer);
        return cliques;
    }

    private static <V extends Vertex, E extends Edge> void getCliques(final Graph<V, E> graph,
                                                                      final Graph.Builder<V, E> clique,
                                                                      final Set<V> remaining,
                                                                      final Set<V> considered,
                                                                      final Consumer<Graph<V, E>> consumer) {
        if (remaining.isEmpty() && considered.isEmpty()) {
            if (!clique.isEmpty()) {
                consumer.accept(clique.build());
            }
            return;
        }

        final Iterator<V> it = remaining.iterator();
        while (it.hasNext()) {
            final V vertex = it.next();
            final Set<V> neighbourSet = new HashSet<>(graph.getNeighbours(vertex));
            clique.addVertex(vertex.getName());
            getCliques(
                    graph,
                    clique,
                    SetUtils.copyRetainAll(remaining, neighbourSet),
                    SetUtils.copyRetainAll(considered, neighbourSet),
                    consumer);
            clique.removeVertex(vertex.getName());
            considered.add(vertex);
            it.remove();
        }
    }

}
