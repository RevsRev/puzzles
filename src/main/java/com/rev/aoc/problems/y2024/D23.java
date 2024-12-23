package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.graph.Graph;
import com.rev.aoc.util.graph.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class D23 extends AocProblem<Long, String> {

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 23);
    }

    @Override
    protected Long partOneImpl() {
        Graph<String> graph = loadResourcesAsGraph();
        Collection<Graph<String>> subGraphsOfSize3 = graph.getConnectedSubgraphsOfSize3();
        long count = 0;
        for (Graph<String> subGraph : subGraphsOfSize3) {
            for (Node<String> element : subGraph.getNodes()) {
                if (element.getValue().startsWith("t")) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    //TODO - Change framework to include different result types!
    @Override
    protected String partTwoImpl() {
        Graph<String> graph = loadResourcesAsGraph();
        Collection<Graph<String>> cliques = graph.getCliques();
        long largestCliqueSize = 0;
        Graph<String> largestClique = null;
        for (Graph<String> clique : cliques) {
            if (clique.getNodes().size() > largestCliqueSize) {
                largestCliqueSize = clique.getNodes().size();
                largestClique = clique;
            }
        }

        List<String> nodeNames = new ArrayList<>(largestClique.getNodes().stream().map(Node::getValue).toList());
        Collections.sort(nodeNames);
        StringBuilder answer = new StringBuilder();
        return String.join(",", nodeNames);
    }

    private Graph<String> loadResourcesAsGraph() {
        List<String> lines = loadResources();
        Map<String, Node<String>> nodes = new HashMap<>();
        for (String line : lines) {
            String[] split = line.split("-");
            String first = split[0];
            String second = split[1];
            Node<String> firstNode = nodes.computeIfAbsent(first, Node::new);
            Node<String> secondNode = nodes.computeIfAbsent(second, Node::new);
            firstNode.addNeighbour(secondNode);
        }
        return new Graph<>(nodes.values());
    }

}
