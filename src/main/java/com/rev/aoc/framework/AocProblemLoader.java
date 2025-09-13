package com.rev.aoc.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public final class AocProblemLoader {
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.aoc.problems";
    private final NavigableMap<AocCoordinate, AocProblem<?, ?>> problems = loadProblems();

    public SortedMap<AocCoordinate, AocProblem<?, ?>> loadProblemsInRange(final AocCoordinate firstAocCoordinate,
                                                                    final AocCoordinate secondAocCoordinate) {
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

    private NavigableMap<AocCoordinate, AocProblem<?, ?>> loadProblems() {
        try {
            NavigableMap<AocCoordinate, AocProblem<?, ?>> retval = new TreeMap<>(AocCoordinate::compareTo);
            ClassPath cp = ClassPath.from(AocEngine.class.getClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = cp.getTopLevelClassesRecursive(AOC_PROBLEMS_PACKAGE);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> clazz = classInfo.load();
                for (Method method : clazz.getDeclaredMethods()) {
                    AocProblemI annotation = method.getAnnotation(AocProblemI.class);
                    if (annotation != null) {
                        AocProblem<?, ?> aocProblem = (AocProblem<?, ?>) clazz.getConstructor().newInstance();
                        retval.put(aocProblem.getCoordinate(), aocProblem);
                    }
                }
            }
            return retval;
        } catch (Exception e) {
            return Collections.emptyNavigableMap();
        }
    }


}
