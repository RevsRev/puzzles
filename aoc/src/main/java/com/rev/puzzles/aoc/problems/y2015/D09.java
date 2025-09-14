package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.problem.ResourceLoader;
import com.rev.puzzles.framework.util.graph.Edge;
import com.rev.puzzles.framework.util.graph.Graph;
import com.rev.puzzles.framework.util.graph.Vertex;
import com.rev.puzzles.framework.util.graph.algo.TravellingSalesman;

import java.util.Collection;
import java.util.function.BiConsumer;

public final class D09 {

    private static final String START = "START";

    @AocProblemI(year = 2015, day = 9, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        final Graph<Vertex, Edge> graph = loadGraph(resourceLoader, false);
        return TravellingSalesman.heldKarp(graph);
    }

    @AocProblemI(year = 2015, day = 9, part = 2)
    @SuppressWarnings("checkstyle:MagicNumber")
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        final Graph<Vertex, Edge> graph = loadGraph(resourceLoader, true);
        return -1 * TravellingSalesman.heldKarp(graph);
    }

    private Graph<Vertex, Edge> loadGraph(final ResourceLoader resourceLoader, final boolean negate) {
        final Graph.Builder<Vertex, Edge> builder = Graph.fromResources(
                resourceLoader.resources(),
                getLineProcessor(negate));
        builder.addVertex(START);

        final Collection<Vertex> vertices = builder.getVertices();
        vertices.forEach(v -> builder.addEdge(START, v.getName(), 0));
        final Graph<Vertex, Edge> graph = builder.build();
        return graph;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private BiConsumer<String, Graph.Builder<Vertex, Edge>> getLineProcessor(final boolean negate) {
        return (s, b) -> {
            String[] vertsAndWeight = s.split(" = ");
            String[] verts = vertsAndWeight[0].split(" to ");
            final String v1 = verts[0];
            final String v2 = verts[1];
            final long weight = Long.parseLong(vertsAndWeight[1]);
            b.addVertex(v1);
            b.addVertex(v2);
            long mult = negate ? -1 : 1;
            b.addEdge(v1, v2, mult * weight);
        };
    }
}
