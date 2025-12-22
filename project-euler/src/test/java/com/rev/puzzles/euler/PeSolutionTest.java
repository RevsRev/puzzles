package com.rev.puzzles.euler;

import com.rev.puzzles.euler.framework.PeCoordinate;
import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestResourceLoader;
import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.ProblemExecutor;
import com.rev.puzzles.framework.framework.impl.AnnotationProblemLoader;
import com.rev.puzzles.framework.framework.impl.DefaultExecutor;
import com.rev.puzzles.framework.framework.impl.DefaultProblemEngine;
import com.rev.puzzles.framework.framework.problem.ProblemResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class PeSolutionTest {

    private static final String LEET_PROBLEMS_PACKAGE = "com.rev.puzzles.euler";

    private static final PeTestResourceLoader TEST_RESOURCE_LOADER = PeTestResourceLoader.create(LEET_PROBLEMS_PACKAGE);

    public static Stream<PeCoordinate> getSolutionsWithTests() {
        return TEST_RESOURCE_LOADER.solutionsWithTests().stream();
    }

    @ParameterizedTest
    @MethodSource("getSolutionsWithTests")
    public void testSolutions(final PeCoordinate coordinate) {
        final AnnotationProblemLoader<?, PeCoordinate> problemLoader =
                new AnnotationProblemLoader<>(LEET_PROBLEMS_PACKAGE, PeProblem.class,
                        problemI -> new PeCoordinate(problemI.number()));

        final CachingExecutionListener executorListener = new CachingExecutionListener();
        final ProblemExecutor<PeCoordinate> executor =
                DefaultExecutor.create(executorListener, TEST_RESOURCE_LOADER);
        final DefaultProblemEngine<PeCoordinate> engine = new DefaultProblemEngine<>(
                problemLoader,
                executor,
                coordinate,
                null
        );

        try {
            engine.run();
        } finally {
            engine.shutdown();
        }

        final List<String> expectedSolutions = TEST_RESOURCE_LOADER.expectedSolutions(coordinate);
        final ProblemResult<PeCoordinate, ?> result = executorListener.getResult(coordinate);
        result.getProblemResult().ifPresentOrElse(
                res -> Assertions.assertEquals(expectedSolutions.get(0), "" + res),
                () -> result.getError().ifPresentOrElse(Assertions::fail,
                        () -> Assertions.fail("No result or error?")
                )
        );
    }

    private static final class CachingExecutionListener implements ExecutorListener<PeCoordinate> {

        private final Map<PeCoordinate, ProblemResult<PeCoordinate, ?>> results = new HashMap<>();

        @Override
        public void executorStarted() {

        }

        @Override
        public void executorSolved(final ProblemResult<PeCoordinate, ?> result) {
            results.put(result.getCoordinate(), result);
        }

        @Override
        public void executorStopped() {

        }

        public ProblemResult<PeCoordinate, ?> getResult(final PeCoordinate coordinate) {
            return results.get(coordinate);
        }
    }

}
