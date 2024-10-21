package com.rev.aoc;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.rev.aoc.problems.AocProblem;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public final class AocEngine implements Runnable {
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.aoc.problems";
    private final AocCoordinate firstAocCoordinate;
    private final AocCoordinate secondAocCoordinate;
    private final AocPart part;

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
        Iterator<Map.Entry<AocCoordinate, AocProblem>> it = problemsInRange.entrySet().iterator();
        while (it.hasNext()) {
            AocProblem problem = it.next().getValue();
            solve(problem);
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
        }
        if (secondAocCoordinate == null) {
            toKey = fromKey;
        }

        SortedMap<AocCoordinate, AocProblem> problemsInRange = problems.subMap(fromKey, true, toKey, true);
        return problemsInRange;
    }

    private void solve(final AocProblem problem) {
        if (AocPart.ALL.equals(part) || AocPart.ONE.equals(part)) {
            System.out.println("PartOne: " + problem.partOne());
        }
        if (AocPart.ALL.equals(part) || AocPart.TWO.equals(part)) {
            System.out.println("PartTwo: " + problem.partTwo());
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
