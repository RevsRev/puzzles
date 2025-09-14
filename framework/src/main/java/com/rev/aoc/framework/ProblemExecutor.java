package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.Problem;
import com.rev.aoc.framework.problem.ProblemCoordinate;

import java.util.List;
import java.util.Map;

public interface ProblemExecutor<T extends ProblemCoordinate<T>> {
    List<Throwable> solve(Iterable<Map.Entry<T, Problem<?>>> problems);
}
