package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.Problem;
import com.rev.aoc.framework.problem.ProblemCoordinate;

import java.util.SortedMap;

public interface ProblemLoader<T extends ProblemCoordinate<T>> {
    SortedMap<T, Problem<?>> loadProblemsInRange(T firstAocCoordinate,
                                                 T secondAocCoordinate);
}
