package com.rev.aoc.util.graph;

import com.rev.aoc.util.set.SetUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final class GraphAlgorithms {

    private GraphAlgorithms() {
    }

    public static <V extends Vertex, E extends Edge> Collection<Graph<V, E>> getCliques(
            final Graph<V, E> graph) {
        Graph.Builder<V, E> clique = new Graph.Builder<>(graph.vertexCreator, graph.edgeCreator, false);
        Set<V> remaining = new HashSet<>(graph.vertices);
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
            clique.addVertex(vertex.name);
            getCliques(
                    graph,
                    clique,
                    SetUtils.copyRetainAll(remaining, neighbourSet),
                    SetUtils.copyRetainAll(considered, neighbourSet),
                    consumer);
            clique.removeVertex(vertex.name);
            considered.add(vertex);
            it.remove();
        }
    }

    //TODO - Write a more general method for size n?
    public static <V extends Vertex, E extends Edge> Collection<Graph<V, E>> getConnectedSubgraphsOfSize3(
            final Graph<V, E> graph) {
        final Collection<Graph<V, E>> subGraphs = new HashSet<>();
        for (V node : graph.vertices) {
            final List<V> neighbours = new ArrayList<>(graph.getNeighbours(node));
            for (int i = 0; i < neighbours.size(); i++) {
                for (int j = i + 1; j < neighbours.size(); j++) {
                    V first = neighbours.get(i);
                    V second = neighbours.get(j);
                    if (!graph.getNeighbours(first).contains(second)) {
                        continue;
                    }
                    final Graph.Builder<V, E> subGraphBuilder = new Graph.Builder<>(
                            graph.vertexCreator,
                            graph.edgeCreator,
                            false);

                    subGraphBuilder.addVertex(node.name);
                    subGraphBuilder.addVertex(first.name);
                    subGraphBuilder.addVertex(second.name);
                    subGraphBuilder.addEdge(node.name, first.name, graph.getEdge(node, first).weight);
                    subGraphBuilder.addEdge(node.name, second.name, graph.getEdge(node, second).weight);

                    final Graph<V, E> subGraph = subGraphBuilder.build();
                    if (!subGraphs.contains(subGraph)) {
                        subGraphs.add(subGraph);
                    }
                }
            }
        }
        return subGraphs;
    }
}
