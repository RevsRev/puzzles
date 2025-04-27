package com.rev.aoc.util.graph;

import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Deprecated
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

}
