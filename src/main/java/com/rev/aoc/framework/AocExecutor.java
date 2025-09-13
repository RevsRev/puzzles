package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocResult;

import java.util.ArrayList;
import java.util.List;

public final class AocExecutor implements ProblemExecutor<AocCoordinate> {

    private final AocPart part;
    private final ExecutorListener executorListener;

    public AocExecutor(final AocPart part,
                       final ExecutorListener executorListener) {
        this.part = part;
        this.executorListener = executorListener;
    }

    @Override
    public List<Throwable> solve(final Iterable<AocProblem<?, ?>> problems) {
        executorListener.executorStarted();
        List<Throwable> errors = new ArrayList<>();
        for (AocProblem<?, ?> problem : problems) {
            AocResult<?, ?> result;
            result = solve(problem);
            if (result.getError().isPresent()) {
                errors.add(result.getError().get());
            }
            executorListener.executorSolved(result);
        }
        executorListener.executorStopped();
        return errors;
    }

    private <P1, P2> AocResult<P1, P2> solve(final AocProblem<P1, P2> problem) {
        try {
            AocResult.Builder<P1, P2> builder = new AocResult.Builder<>();
            builder.setCoordinate(problem.getCoordinate());
            if (AocPart.ALL.equals(part) || AocPart.ONE.equals(part)) {
                long time = System.nanoTime();
                P1 result = problem.partOne().solve();
                time = System.nanoTime() - time;
                builder.setPartOne(result);
                builder.setPartOneTime(time);
            }
            if (AocPart.ALL.equals(part) || AocPart.TWO.equals(part)) {
                long time = System.nanoTime();
                P2 result = problem.partTwo().solve();
                time = System.nanoTime() - time;
                builder.setPartTwo(result);
                builder.setPartTwoTime(time);
            }
            return builder.build();
        } catch (Throwable t) {
            return AocResult.error(problem.getCoordinate(), t);
        }
    }
}
