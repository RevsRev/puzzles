package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.graph.Edge;
import com.rev.aoc.util.graph.Graph;
import com.rev.aoc.util.graph.Vertex;
import com.rev.aoc.util.graph.algo.TravellingSalesman;

import java.util.Collection;
import java.util.function.BiConsumer;

public final class D09 extends AocProblem<Long, Long> {

    private static final String START = "START";

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 9);
    }

    @Override
    protected Long partOneImpl() {
        final Graph<Vertex, Edge> graph = loadGraph(false);
        return TravellingSalesman.heldKarp(graph);
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected Long partTwoImpl() {
        final Graph<Vertex, Edge> graph = loadGraph(true);
        return -1 * TravellingSalesman.heldKarp(graph);
    }

    private Graph<Vertex, Edge> loadGraph(final boolean negate) {
        final Graph.Builder<Vertex, Edge> builder = Graph.fromResources(loadResources(), getLineProcessor(negate));
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
