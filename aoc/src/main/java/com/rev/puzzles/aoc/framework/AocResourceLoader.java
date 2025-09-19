package com.rev.puzzles.aoc.framework;

import com.rev.puzzles.aoc.framework.load.AocInputLoader;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;

import java.io.IOException;
import java.util.Optional;
import java.util.ServiceLoader;

public final class AocResourceLoader implements ResourceLoader<AocCoordinate> {

    public AocResourceLoader() {
    }


    private static AocInputLoader loadAocProblemLoader() {
        ServiceLoader<AocInputLoader> inputLoaders = ServiceLoader.load(AocInputLoader.class);
        long count = inputLoaders.stream().count();
        if (count > 1) {
            System.out.println("[\u001B[31mWARNING\u001B[0m] Multiple AocInputLoader instances found.");
        }
        Optional<AocInputLoader> inputLoaderOptional = inputLoaders.findFirst();
        if (inputLoaderOptional.isEmpty()) {
            throw new RuntimeException("[\u001B[31mFatal\u001B[0m] No AocInputLoader found");
        }

        AocInputLoader inputLoader = inputLoaderOptional.get();
        if (count > 1) {
            System.out.println("[\u001B[31mWARNING\u001B[0m] Loaded " + inputLoader.getClass().getName());
        }
        return inputLoader;
    }

    public static ProblemResourceLoader loadResources(final AocCoordinate coordinate) {
        return () -> {
            try {
                return loadAocProblemLoader().load(coordinate);
            } catch (IOException e) {
                String msg = String.format("Could not load resource for problem %s", coordinate);
                throw new ProblemExecutionException(msg, e);
            }
        };
    }

    @Override
    public ProblemResourceLoader getProblemResourceLoader(final AocCoordinate coordinate) {
        return loadResources(coordinate);
    }
}
