package com.rev.aoc.util.graph;

import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * (Currently undirrected).
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

    private Graph(final Function<String, V> vertexCreator,
                  final Function<Long, E> edgeCreator) {
        this(new HashSet<>(), new HashSet<>(), new HashMap<>(), vertexCreator, edgeCreator);
    }

    private Graph(final Graph<V, E> copy) {
        this(new HashSet<>(
                copy.vertices),
                new HashSet<>(copy.edges),
                new HashMap<>(copy.vertexEdgeMap),
                copy.vertexCreator,
                copy.edgeCreator);
    }

    private Graph(final Set<V> vertices,
                  final Set<E> edges,
                  final Map<V, Map<V, E>> vertexEdgeMap,
                  final Function<String, V> vertexCreator,
                  final Function<Long, E> edgeCreator) {
        this.vertices = vertices;
        this.edges = edges;
        this.vertexEdgeMap = vertexEdgeMap;
        this.vertexCreator = vertexCreator;
        this.edgeCreator = edgeCreator;
    }

    public boolean containsVertex(final V v) {
        return vertices.contains(v);
    }

    public boolean containsEdge(final V v1, final V v2, final E e) {
        return vertexEdgeMap.get(v1).containsKey(e)
                && vertexEdgeMap.get(v2).containsKey(e);
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
            throw new IllegalArgumentException("Graph does not contain edge");
        }
        return new HashSet<>(vertexEdgeMap.get(node).keySet());
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public E getEdge(final V v1, final V v2) {
        if (!containsVertex(v1) || !containsVertex(v2)) {
            throw new IllegalArgumentException("Graph does not contain the vertices requested");
        }
        return vertexEdgeMap.get(v1).get(v2);
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
        if (containsEdge(v1, v2, e)) {
            throw new IllegalStateException("Trying to add a vertex/edge that already exists");
        }
        edges.add(e);
        vertexEdgeMap.get(v1).put(v2, e);
        vertexEdgeMap.get(v2).put(v1, e);
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
                boolean lenient) {
            this.vertexCreator = vertexCreator;
            this.edgeCreator = edgeCreator;
            this.lenient = lenient;
            this.graph = new Graph<>(vertexCreator, edgeCreator);
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
            if (!lenient || !graph.containsEdge(v1, v2, e)) {
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
    }
}
