package com.rev.aoc.util.graph.algo;

import com.rev.aoc.util.graph.Edge;
import com.rev.aoc.util.graph.Graph;
import com.rev.aoc.util.graph.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class SubGraphs {

    private SubGraphs() {
    }

    //TODO - Write a more general method for size n?
    public static <V extends Vertex, E extends Edge> Collection<Graph<V, E>> getConnectedSubgraphsOfSize3(
            final Graph<V, E> graph) {
        final Collection<Graph<V, E>> subGraphs = new HashSet<>();
        for (V node : graph.getVertices()) {
            final List<V> neighbours = new ArrayList<>(graph.getNeighbours(node));
            for (int i = 0; i < neighbours.size(); i++) {
                for (int j = i + 1; j < neighbours.size(); j++) {
                    V first = neighbours.get(i);
                    V second = neighbours.get(j);
                    if (!graph.getNeighbours(first).contains(second)) {
                        continue;
                    }
                    final Graph.Builder<V, E> subGraphBuilder = new Graph.Builder<>(
                            graph.getVertexCreator(),
                            graph.getEdgeCreator(),
                            false);

                    subGraphBuilder.addVertex(node.getName());
                    subGraphBuilder.addVertex(first.getName());
                    subGraphBuilder.addVertex(second.getName());
                    subGraphBuilder.addEdge(
                            node.getName(),
                            first.getName(),
                            graph.getEdge(node, first).get().getWeight());
                    subGraphBuilder.addEdge(
                            node.getName(),
                            second.getName(),
                            graph.getEdge(node, second).get().getWeight());

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
