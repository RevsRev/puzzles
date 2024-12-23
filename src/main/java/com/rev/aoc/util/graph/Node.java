package com.rev.aoc.util.graph;

import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public final class Node<T> {
    private final T value;
    private final Set<Node<T>> neighbours = new HashSet<>();

    public Node(final T value) {
        this.value = value;
    }

    public void addNeighbour(final Node<T> other) {
        if (neighbours.contains(other)) {
            return;
        }
        neighbours.add(other);
        other.neighbours.add(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Graph<T> traverse() {
        return traverse(Integer.MAX_VALUE);
    }
    public Graph<T> traverse(int depth) {
        Set<Node<T>> visited = new HashSet<>();
        for (Node<T> node : neighbours) {
            traverse(node, visited, depth - 1);
        }
        return new Graph<>(visited);
    }
    private static <T> void traverse(final Node<T> node, final Set<Node<T>> visited, final int depth) {
        if (depth == 0 || visited.contains(node)) {
            return;
        }
        visited.add(node);
        for (Node<T> neighbour : node.neighbours) {
            traverse(neighbour, visited, depth - 1);
        }
    }

}
