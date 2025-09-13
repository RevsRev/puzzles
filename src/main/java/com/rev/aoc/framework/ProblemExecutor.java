package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.ProblemCoordinate;

import java.util.List;

public interface ProblemExecutor<T extends ProblemCoordinate<T>> {
    List<Throwable> solve(Iterable<AocProblem<?, ?>> problems);
}
