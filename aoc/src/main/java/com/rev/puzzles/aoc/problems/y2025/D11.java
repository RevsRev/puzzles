package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.graph.Edge;
import com.rev.puzzles.math.graph.Graph;
import com.rev.puzzles.math.graph.Vertex;
import com.rev.puzzles.math.graph.algo.Routes;

import java.util.List;

public final class D11 {

    @AocProblemI(year = 2025, day = 11, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {

        Graph<Vertex, Edge> graph = parseGraph(resourceLoader);
        return Routes.numRoutesFromAToB(graph, new Vertex("you"), new Vertex("out"));
    }

    @AocProblemI(year = 2025, day = 11, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        Graph<Vertex, Edge> graph = parseGraph(resourceLoader);

        final long fromSvrToDac = Routes.numRoutesFromAToB(graph, new Vertex("svr"), new Vertex("dac"));
        final long fromSvrToFft = Routes.numRoutesFromAToB(graph, new Vertex("svr"), new Vertex("fft"));
        final long fromDacToFft = Routes.numRoutesFromAToB(graph, new Vertex("dac"), new Vertex("fft"));
        final long fromFftToDac = Routes.numRoutesFromAToB(graph, new Vertex("fft"), new Vertex("dac"));
        final long fromFftToOut = Routes.numRoutesFromAToB(graph, new Vertex("fft"), new Vertex("out"));
        final long fromDacToOut = Routes.numRoutesFromAToB(graph, new Vertex("dac"), new Vertex("out"));

        final long fromSvrToOutWithDacFirstThenFft = fromSvrToDac * fromDacToFft * fromFftToOut;
        final long fromSvrToOutWithFftFirstThenDac = fromSvrToFft * fromFftToDac * fromDacToOut;

        return fromSvrToOutWithDacFirstThenFft + fromSvrToOutWithFftFirstThenDac;
    }

    private static Graph<Vertex, Edge> parseGraph(final ProblemResourceLoader<List<String>> resourceLoader) {
        Graph<Vertex, Edge> graph = Graph.fromResources(
                resourceLoader.resources(),
                (string, builder) -> {
                    final String[] split = string.split(":");
                    final String sourceVertexName = split[0];
                    builder.addVertex(sourceVertexName);
                    final String[] targetVertices = split[1].trim().split("\\s+");
                    for (final String targetVertex : targetVertices) {
                        builder.addVertex(targetVertex);
                        builder.addEdge(sourceVertexName, targetVertex, 1);
                    }
                },
                true
        ).build();
        return graph;
    }
}
