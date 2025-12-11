package com.rev.puzzles.math.graph.algo;

import com.rev.puzzles.algo.dp.DynamicProgram;
import com.rev.puzzles.math.graph.Edge;
import com.rev.puzzles.math.graph.Graph;
import com.rev.puzzles.math.graph.Vertex;

import java.util.function.BiFunction;

public class Routes {

    /**
     * Assumes a directed graph going from start to end
     * @param graph
     * @param startVertex
     * @param endVertex
     * @return
     * @param <V>
     * @param <E>
     */
    public static <V extends Vertex, E extends Edge> long numRoutesFromAToB(
            final Graph<V, E> graph,
            final V startVertex,
            final V endVertex
    ) {
        final DynamicProgram<V, Long> dp = new DynamicProgram<>(new RouteDpFunction<>(graph, endVertex));
        return dp.compute(startVertex);
    }

    private static final class RouteDpFunction<V extends Vertex, E extends Edge> implements BiFunction<DynamicProgram<V, Long>, V, Long> {

        final Graph<V, E> graph;
        private final Vertex endVertex;

        public RouteDpFunction(final Graph<V, E> graph, final V endVertex) {
            this.graph = graph;
            this.endVertex = endVertex;
        }

        @Override
        public Long apply(final DynamicProgram<V, Long> dp, final V v) {
            if (v.equals(endVertex)) {
                return 1L;
            }

            long numRoutes = 0;
            for (final V neighbour : graph.getNeighbours(v)) {
                numRoutes += dp.compute(neighbour);
            }
            return numRoutes;
        }
    }

}
