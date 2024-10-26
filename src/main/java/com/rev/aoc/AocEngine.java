package com.rev.aoc;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.rev.aoc.problems.AocProblem;
import com.rev.aoc.util.AocResult;
import com.rev.aoc.util.AocResultPrinter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public final class AocEngine implements Runnable {
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.aoc.problems";
    private final AocCoordinate firstAocCoordinate;
    private final AocCoordinate secondAocCoordinate;
    private final AocPart part;
    private final AocResultPrinter printer = new AocResultPrinter();

    @Setter
    private boolean debug = false;

    public AocEngine(final AocCoordinate firstAocCoordinate,
                     final AocCoordinate secondAocCoordinate,
                     final AocPart part) {
        this.firstAocCoordinate = firstAocCoordinate;
        this.secondAocCoordinate = secondAocCoordinate;
        this.part = part;
    }


    @Override
    public void run() {
        NavigableMap<AocCoordinate, AocProblem> problems = loadProblems();
        SortedMap<AocCoordinate, AocProblem> problemsInRange = getProblemsInRange(problems);
        if (problemsInRange == null) {
            return;
        }
        List<Throwable> errors = solveAndPrint(problemsInRange.values());
        for (int i = 0; i < errors.size() && debug; i++) {
            errors.get(i).printStackTrace();
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

        SortedMap<AocCoordinate, AocProblem> problemsInRange = problems.subMap(fromKey, true, toKey, true);
        return problemsInRange;
    }

    private List<Throwable> solveAndPrint(final Iterable<AocProblem> problems) {
        List<Throwable> errors = new ArrayList<>();
        for (AocProblem problem : problems) {
            AocResult result;
            result = solve(problem);
            if (result.getError().isPresent()) {
                errors.add(result.getError().get());
            }
            printer.printResult(result);
        }
        printer.printSeparator();
        return errors;
    }
    private AocResult solve(final AocProblem problem) {
        try {
            AocResult.Builder builder = new AocResult.Builder();
            builder.setCoordinate(problem.getCoordinate());
            if (AocPart.ALL.equals(part) || AocPart.ONE.equals(part)) {
                builder.setPartOne(problem.partOne());
            }
            if (AocPart.ALL.equals(part) || AocPart.TWO.equals(part)) {
                builder.setPartTwo(problem.partTwo());
            }
            return builder.build();
        } catch (Throwable t) {
            return AocResult.error(problem.getCoordinate(), t);
        }
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
