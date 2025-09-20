package com.rev.puzzles.leet.gen;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.problem.ProblemResult;
import com.rev.puzzles.leet.framework.LeetCoordinate;

import java.io.FileWriter;
import java.io.IOException;


public final class LeetExecutionListenerFileWriter implements ExecutorListener<LeetCoordinate> {

    private final String leetProblemsDirectory;

    public LeetExecutionListenerFileWriter(final String leetProblemsDirectory) {
        this.leetProblemsDirectory = leetProblemsDirectory;
    }

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void executorSolved(final ProblemResult<LeetCoordinate, ?> result) {
        result.getProblemResult().ifPresentOrElse(r -> {
            try (FileWriter fileWriter = new FileWriter(
                    String.format("%s/L%s.txt", leetProblemsDirectory, result.getCoordinate()))) {
                fileWriter.write(r.toString());
            } catch (final IOException e) {
                System.out.println(String.format("ERROR writing generated solution to file: '%s'", e.getMessage()));
            }
        }, () -> System.out.println("ERROR - no problem result"));

    }

    @Override
    public void executorStopped() {
    }
}
