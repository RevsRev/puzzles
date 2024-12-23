package com.rev.aoc.util.graph;

import com.rev.aoc.util.set.SetUtils;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public final class Graph<T> {
    private final Set<Node<T>> nodes = new HashSet<>();

    public Graph(final Collection<Node<T>> nodes) {
        this.nodes.addAll(nodes);
    }

    public Collection<Graph<T>> getDisjointSubgraphs() {
        Set<Node<T>> nodesToCheck = new HashSet<>(nodes);
        Collection<Graph<T>> subgraphs = new HashSet<>();
        for (Node<T> node: nodes) {
            if (nodesToCheck.contains(node)) {
                Graph<T> traversed = node.traverse();
                nodesToCheck.removeAll(traversed.nodes);
                subgraphs.add(traversed);
            }
        }
        return subgraphs;
    }

    public Collection<Graph<T>> getCliques() {
        Set<Node<T>> clique = new HashSet<>();
        Set<Node<T>> remaining = new HashSet<>(nodes);
        Set<Node<T>> considered = new HashSet<>();

        final Set<Graph<T>> cliques = new HashSet<>();
        Consumer<Set<Node<T>>> consumer = set -> cliques.add(new Graph<>(set));
        getCliques(clique, remaining, considered, consumer);
        return cliques;
    }

    private void getCliques(final Set<Node<T>> clique,
                            final Set<Node<T>> remaining,
                            final Set<Node<T>> considered,
                            final Consumer<Set<Node<T>>> consumer) {
        if (remaining.isEmpty() && considered.isEmpty()) {
            if (!clique.isEmpty()) {
                consumer.accept(clique);
            }
            return;
        }

        Iterator<Node<T>> it = remaining.iterator();
        while (it.hasNext()) {
            Node<T> node = it.next();
            Set<Node<T>> neighbourSet = node.getNeighbours();
            clique.add(node);
            getCliques(clique,
                    SetUtils.copyRetainAll(remaining, neighbourSet),
                    SetUtils.copyRetainAll(considered, neighbourSet),
                    consumer);
            clique.remove(node);
            considered.add(node);
            it.remove();
        }
    }

    public Collection<Graph<T>> getConnectedSubgraphsOfSizeN(int n) {
        Collection<Graph<T>> allConnected = new HashSet<>();

        if (n == 1) {
            for (Node<T> node : nodes) {
                allConnected.add(new Graph<>(Set.of(node)));
            }
            return allConnected;
        }

        Collection<Graph<T>> connected = getConnectedSubgraphsOfSizeN(n - 1);
        for (Node<T> node : nodes) {
            for (Graph<T> graph : connected) {
                if (!graph.nodes.contains(node)) {
                    boolean canConnect = true;
                    for (Node<T> gn : graph.getNodes()) {
                        if (!gn.getNeighbours().contains(node)) {
                            canConnect = false;
                            break;
                        }
                    }
                    if (canConnect) {
                        Graph<T> add = new Graph<>(graph.nodes);
                        add.getNodes().add(node);
                        allConnected.add(add);
                    }
                }
            }
        }
        return allConnected;
    }

    //TODO - Generalise!
    public Collection<Graph<T>> getConnectedSubgraphsOfSize3() {
        Collection<Graph<T>> subGraphs = new HashSet<>();
        for (Node<T> node : nodes) {
            List<Node<T>> neighbours = node.getNeighbours().stream().toList();
            for (int i = 0; i < neighbours.size(); i++) {
                for (int j = i + 1; j < neighbours.size(); j++) {
                    if (!neighbours.get(i).getNeighbours().contains(neighbours.get(j))) {
                        continue;
                    }
                    Graph<T> subGraph = new Graph<>(List.of(node, neighbours.get(i), neighbours.get(j)));
                    if (!subGraphs.contains(subGraph)) {
                        subGraphs.add(subGraph);
                    }
                }
            }
        }
        return subGraphs;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Graph<?> graph = (Graph<?>) o;
        return nodes.containsAll(graph.nodes) && graph.nodes.containsAll(nodes);
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Node<T> node : nodes) {
            hashCode += node.hashCode();
        }
        return hashCode;
    }
}
