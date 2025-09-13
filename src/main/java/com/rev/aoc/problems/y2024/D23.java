package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;
import com.rev.aoc.util.graph.Edge;
import com.rev.aoc.util.graph.Graph;
import com.rev.aoc.util.graph.Vertex;
import com.rev.aoc.util.graph.algo.Cliques;
import com.rev.aoc.util.graph.algo.SubGraphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class D23 extends AocProblem<Long, String> {

    @AocProblemI(year = 2024, day = 23, part = 1)
    @Override
    protected Long partOneImpl(final ResourceLoader resourceLoader) {
        Graph<Vertex, Edge> graph = loadResourcesAsGraph(resourceLoader);
        Collection<Graph<Vertex, Edge>> subGraphsOfSize3 = SubGraphs.getConnectedSubgraphsOfSize3(graph);
        long count = 0;
        for (Graph<Vertex, Edge> subGraph : subGraphsOfSize3) {
            for (Vertex element : subGraph.getVertices()) {
                if (element.getName().startsWith("t")) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    @AocProblemI(year = 2024, day = 23, part = 2)
    @Override
    protected String partTwoImpl(final ResourceLoader resourceLoader) {
        Graph<Vertex, Edge> graph = loadResourcesAsGraph(resourceLoader);
        Collection<Graph<Vertex, Edge>> cliques = Cliques.getCliques(graph);
        long largestCliqueSize = 0;
        Graph<Vertex, Edge> largestClique = null;
        for (Graph<Vertex, Edge> clique : cliques) {
            if (clique.getVertices().size() > largestCliqueSize) {
                largestCliqueSize = clique.getVertices().size();
                largestClique = clique;
            }
        }

        List<String> nodeNames = new ArrayList<>(largestClique.getVertices().stream().map(Vertex::getName).toList());
        Collections.sort(nodeNames);
        StringBuilder answer = new StringBuilder();
        return String.join(",", nodeNames);
    }

    private Graph<Vertex, Edge> loadResourcesAsGraph(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        return Graph.fromResources(lines, (s, b) -> {
            String[] split = s.split("-");
            String first = split[0];
            String second = split[1];
            b.addVertex(first);
            b.addVertex(second);
            b.addEdge(first, second, 1);
        }).build();
    }

}
