package com.rev.aoc.util.graph;

import lombok.Getter;

import java.util.Objects;

@SuppressWarnings("checkstyle:VisibilityModifier")
@Getter
public final class Edge {
    final long weight;

    public Edge(final long weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge edge = (Edge) o;
        return weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(weight);
    }
}
