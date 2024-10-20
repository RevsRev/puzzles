package com.rev.aoc;

import com.google.common.collect.ImmutableSet;
import com.rev.aoc.problems.AocProblem;
import com.google.common.reflect.ClassPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AocEngine implements Runnable {
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.aoc.problems";
    private final AocCoordinate firstAocCoordinate;
    private final AocCoordinate secondAocCoordinate;

    public AocEngine(final AocCoordinate firstAocCoordinate,
                     final AocCoordinate secondAocCoordinate) {
        this.firstAocCoordinate = firstAocCoordinate;
        this.secondAocCoordinate = secondAocCoordinate;
    }


    @Override
    public void run() {
        List<AocProblem> problems = loadProblems();
        System.out.println("Executing Advent Of Code problems...");
        for (AocProblem problem : problems) {
            System.out.println("" + problem.partOne());
        }
    }

    private List<AocProblem> loadProblems() {
        try {
            List<AocProblem> problems = new ArrayList<>();
            ClassPath cp = ClassPath.from(AocEngine.class.getClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = cp.getTopLevelClassesRecursive(AOC_PROBLEMS_PACKAGE);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> clazz = classInfo.load();
                Class<?> superClazz = clazz.getSuperclass();
                if (AocProblem.class.equals(superClazz)) {
                    Class<? extends AocProblem> problemClazz = (Class<? extends AocProblem>) clazz;
                    AocProblem aocProblem = problemClazz.getConstructor().newInstance();
                    problems.add(aocProblem);
                }
            }
            return problems;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
