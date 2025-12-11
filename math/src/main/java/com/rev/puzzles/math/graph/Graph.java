package com.rev.puzzles.math.graph;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * (Currently undirrected).
 *
 * @param <V>
 * @param <E>
 */
@SuppressWarnings("checkstyle:VisibilityModifier")
public final class Graph<V extends Vertex, E extends Edge> {

    final Set<V> vertices;
    final Set<E> edges;
    final Map<V, Map<V, E>> vertexEdgeMap;

    @Getter
    final Function<String, V> vertexCreator;
    @Getter
    final Function<Long, E> edgeCreator;
    private final boolean directed;

    public static Builder<Vertex, Edge> fromResources(
            final List<String> lines,
            final BiConsumer<String, Builder<Vertex, Edge>> lineProcessor) {
        return fromResources(lines, lineProcessor, false);
    }

    public static Builder<Vertex, Edge> fromResources(
            final List<String> lines,
            final BiConsumer<String, Builder<Vertex, Edge>> lineProcessor,
            final boolean directed) {
        return fromResources(lines, lineProcessor, Vertex::new, Edge::new, directed);
    }

    public static <V extends Vertex, E extends Edge> Builder<V, E> fromResources(
            final List<String> lines,
            final BiConsumer<String, Builder<V, E>> lineProcessor,
            final Function<String, V> vertexCreator,
            final Function<Long, E> edgeCreator,
            final boolean directed) {
        final Graph.Builder<V, E> builder = new Graph.Builder<>(vertexCreator, edgeCreator, true, directed);
        for (final String line : lines) {
            lineProcessor.accept(line, builder);
        }
        return builder;
    }

    private Graph(final Function<String, V> vertexCreator,
                  final Function<Long, E> edgeCreator,
                  final boolean directed) {
        this(new HashSet<>(), new HashSet<>(), new HashMap<>(), vertexCreator, edgeCreator, directed);
    }

    private Graph(final Graph<V, E> copy) {
        this(new HashSet<>(
                        copy.vertices),
                new HashSet<>(copy.edges),
                new HashMap<>(copy.vertexEdgeMap),
                copy.vertexCreator,
                copy.edgeCreator,
                copy.directed);
    }

    private Graph(final Set<V> vertices,
                  final Set<E> edges,
                  final Map<V, Map<V, E>> vertexEdgeMap,
                  final Function<String, V> vertexCreator,
                  final Function<Long, E> edgeCreator,
                  final boolean directed) {
        this.vertices = vertices;
        this.edges = edges;
        this.vertexEdgeMap = vertexEdgeMap;
        this.vertexCreator = vertexCreator;
        this.edgeCreator = edgeCreator;
        this.directed = directed;
    }

    public static <V extends Vertex, E extends Edge> Graph<V, E> aggregate(
            final Graph<V, E> directedGraph,
            final BiFunction<Optional<E>, Optional<E>, Long> edgeAggregator) {
        if (!directedGraph.directed) {
            throw new IllegalArgumentException();
        }

        Builder<V, E> builder = new Builder<>(directedGraph.vertexCreator, directedGraph.edgeCreator, false);
        List<V> vertices = new ArrayList<>(directedGraph.getVertices());
        vertices.forEach(v -> builder.addVertex(v.getName()));
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                V first = vertices.get(i);
                V second = vertices.get(j);
                Optional<E> firstToSecond = directedGraph.getEdge(first, second);
                Optional<E> secondToFirst = directedGraph.getEdge(second, first);
                builder.addEdge(first.name, second.name, edgeAggregator.apply(firstToSecond, secondToFirst));
            }
        }
        return builder.build();
    }

    public boolean containsVertex(final V v) {
        return vertices.contains(v);
    }

    public boolean containsEdge(final V v1, final V v2) {
        return !vertexEdgeMap.get(v1).isEmpty()
                && vertexEdgeMap.get(v1).containsKey(v2);
    }

    public Optional<E> getEdge(final V v1, final V v2) {
        if (!containsVertex(v1) || !containsVertex(v2)) {
            throw new IllegalArgumentException("Graph does not contain the vertices requested");
        }
        return Optional.ofNullable(vertexEdgeMap.get(v1).get(v2));
    }

    private void addVertex(final V v) {
        if (vertices.contains(v)) {
            throw new IllegalStateException("Trying to add a vertex that already exists");
        }
        vertices.add(v);
        vertexEdgeMap.put(v, new HashMap<>());
    }

    private void removeVertex(final V v) {
        if (!vertices.contains(v)) {
            throw new IllegalStateException("Trying to remove a vertex that doesn't exist");
        }
        vertices.remove(v);
        Map<V, E> evMap = vertexEdgeMap.get(v);
        for (V value : evMap.keySet()) {
            vertexEdgeMap.get(value).remove(v);
        }
        vertexEdgeMap.remove(v);
    }

    public Collection<V> getNeighbours(final V node) {
        if (!containsVertex(node)) {
            throw new IllegalArgumentException("Graph does not contain node %s".formatted(node.name));
        }
        return new HashSet<>(vertexEdgeMap.get(node).keySet());
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Graph<V, E> graph = (Graph<V, E>) o; //TODO - Hmmmm....

        final String sortedVs = String.join(",", vertices.stream().map(V::getName).sorted().toList());
        final String sortedEs = String.join(",", edges.stream().map(e -> "" + e.weight).sorted().toList());
        final String oSortedVs = String.join(",", graph.vertices.stream().map(V::getName).sorted().toList());
        final String oSortedEs = String.join(",", graph.edges.stream().map(e -> "" + e.weight).sorted().toList());

        //TODO - Rubbish, improve later...
        return Objects.equals(sortedVs, oSortedVs) && Objects.equals(sortedEs, oSortedEs);

//        return Objects.equals(vertices, graphV2.vertices)
//                && Objects.equals(edges, graphV2.edges)
//                && Objects.equals(vertexEdgeMap, graphV2.vertexEdgeMap)
//                && Objects.equals(vertexCreator, graphV2.vertexCreator)
//                && Objects.equals(edgeCreator, graphV2.edgeCreator);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(vertices, edges, vertexEdgeMap, vertexCreator, edgeCreator);
        final String sortedVs = String.join(",", vertices.stream().map(V::getName).sorted().toList());
        final String sortedEs = String.join(",", edges.stream().map(e -> "" + e.weight).sorted().toList());
        return Objects.hash(sortedVs, sortedEs); //TODO - Rubbish, improve later...
    }

    private void addEdge(final V v1, final V v2, final E e) {
        if (containsEdge(v1, v2)) {
            throw new IllegalStateException("Trying to add a vertex/edge that already exists");
        }
        edges.add(e);
        vertexEdgeMap.get(v1).put(v2, e);
        if (!directed) {
            vertexEdgeMap.get(v2).put(v1, e);
        }
    }

    public Collection<V> getVertices() {
        return new HashSet<>(vertices);
    }

    public static final class Builder<V extends Vertex, E extends Edge> {
        private final Function<String, V> vertexCreator;
        private final Function<Long, E> edgeCreator;
        private final Graph<V, E> graph;
        private final boolean lenient;

        public Builder(
                final Function<String, V> vertexCreator,
                final Function<Long, E> edgeCreator,
                final boolean lenient) {
            this(vertexCreator, edgeCreator, lenient, false);
        }

        public Builder(
                final Function<String, V> vertexCreator,
                final Function<Long, E> edgeCreator,
                final boolean lenient,
                final boolean directed) {
            this.vertexCreator = vertexCreator;
            this.edgeCreator = edgeCreator;
            this.lenient = lenient;
            this.graph = new Graph<>(vertexCreator, edgeCreator, directed);
        }

        public Graph<V, E> build() {
            return new Graph<>(graph);
        }

        public Builder<V, E> addVertex(final String name) {
            final V v = vertexCreator.apply(name);
            if (!lenient || !graph.containsVertex(v)) {
                graph.addVertex(v);
            }
            return this;
        }

        public Builder<V, E> addEdge(final String v1Name, final String v2Name, final long edgeWeight) {
            final V v1 = vertexCreator.apply(v1Name);
            final V v2 = vertexCreator.apply(v2Name);
            final E e = edgeCreator.apply(edgeWeight);
            if (!lenient || !graph.containsEdge(v1, v2)) {
                graph.addEdge(v1, v2, e);
            }
            return this;
        }

        public boolean isEmpty() {
            return graph.isEmpty();
        }

        public void removeVertex(final String name) {
            graph.removeVertex(vertexCreator.apply(name));
        }

        public Collection<V> getVertices() {
            return graph.getVertices();
        }
    }
}
