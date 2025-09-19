package com.rev.puzzles.framework.framework;

import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;

public interface ResourceLoader<C extends ProblemCoordinate<C>> {
    ProblemResourceLoader getProblemResourceLoader(C coordinate);
}
