package com.rev.puzzles.framework.framework;

import com.rev.puzzles.framework.framework.problem.Problem;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;

import java.util.List;
import java.util.Map;

public interface ProblemExecutor<T extends ProblemCoordinate<T>> {
    List<Throwable> solve(Iterable<Map.Entry<T, Problem<?>>> problems);
}
