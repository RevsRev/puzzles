package com.rev.aoc.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.AocProblem;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public final class AocEngine implements Runnable {
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.aoc.problems";
    private final AocCoordinate firstAocCoordinate;
    private final AocCoordinate secondAocCoordinate;
    private final AocExecutor executor;
    private final AocVisualiser visualiser;

    @Setter
    private boolean debug = false;
    @Setter
    private boolean visualise = false;

    public AocEngine(final AocCoordinate firstAocCoordinate,
                     final AocCoordinate secondAocCoordinate,
                     final AocPart part) {
        this.firstAocCoordinate = firstAocCoordinate;
        this.secondAocCoordinate = secondAocCoordinate;
        this.visualiser = new AocVisualiser();
        this.executor = new AocExecutor(part, new ExecutorListenerPrinter());
    }


    @Override
    public void run() {
        NavigableMap<AocCoordinate, AocProblem> problems = loadProblems();
        SortedMap<AocCoordinate, AocProblem> problemsInRange = getProblemsInRange(problems);
        if (problemsInRange == null) {
            return;
        }
        if (visualise) {
            List<Throwable> errors = visualiser.visualise(problemsInRange.values());
            printErrors(errors);
            return;
        }

        List<Throwable> errors = executor.solve(problemsInRange.values());
        printErrors(errors);
    }

    private void printErrors(final List<Throwable> errors) {
        for (int i = 0; i < errors.size() && debug; i++) {
            errors.get(i).printStackTrace(System.out);
        }
    }

    private SortedMap<AocCoordinate, AocProblem> getProblemsInRange(
            final NavigableMap<AocCoordinate, AocProblem> problems) {
        if (problems.isEmpty()) {
            //TODO - Probably want some logging here!
            return null;
        }

        AocCoordinate fromKey = firstAocCoordinate;
        AocCoordinate toKey = secondAocCoordinate;
        if (fromKey == null) {
            fromKey = problems.firstKey();
            if (toKey == null) {
                toKey = problems.lastKey();
            }
        }
        if (toKey == null) {
            toKey = fromKey;
        }

        return problems.subMap(fromKey, true, toKey, true);
    }

    private NavigableMap<AocCoordinate, AocProblem> loadProblems() {
        try {
            NavigableMap<AocCoordinate, AocProblem> problems = new TreeMap<>(AocCoordinate::compareTo);
            ClassPath cp = ClassPath.from(AocEngine.class.getClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = cp.getTopLevelClassesRecursive(AOC_PROBLEMS_PACKAGE);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> clazz = classInfo.load();
                Class<?> superClazz = clazz.getSuperclass();
                if (AocProblem.class.equals(superClazz)) {
                    Class<? extends AocProblem> problemClazz = (Class<? extends AocProblem>) clazz;
                    AocProblem aocProblem = problemClazz.getConstructor().newInstance();
                    problems.put(aocProblem.getCoordinate(), aocProblem);
                }
            }
            return problems;
        } catch (Exception e) {
            return Collections.emptyNavigableMap();
        }
    }
}
