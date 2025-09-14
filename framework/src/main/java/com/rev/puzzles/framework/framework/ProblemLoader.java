package com.rev.puzzles.framework.framework;

import com.rev.puzzles.framework.framework.problem.Problem;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;

import java.util.SortedMap;

public interface ProblemLoader<T extends ProblemCoordinate<T>> {
    SortedMap<T, Problem<?>> loadProblemsInRange(T firstAocCoordinate,
                                                 T secondAocCoordinate);
}
