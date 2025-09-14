package com.rev.aoc.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.Problem;
import com.rev.aoc.framework.problem.ProblemExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public final class AocProblemLoader implements ProblemLoader<AocCoordinate> {
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.aoc.problems";
    private final NavigableMap<AocCoordinate, Problem<?>> problems = loadProblems();

    @Override
    public SortedMap<AocCoordinate, Problem<?>> loadProblemsInRange(final AocCoordinate firstAocCoordinate,
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

    private NavigableMap<AocCoordinate, Problem<?>> loadProblems() {
        try {
            NavigableMap<AocCoordinate, Problem<?>> retval = new TreeMap<>(AocCoordinate::compareTo);
            ClassPath cp = ClassPath.from(ProblemEngine.class.getClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = cp.getTopLevelClassesRecursive(AOC_PROBLEMS_PACKAGE);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> clazz = classInfo.load();
                for (Method method : clazz.getDeclaredMethods()) {
                    AocProblemI annotation = method.getAnnotation(AocProblemI.class);
                    if (annotation != null) {
                        AocCoordinate coordinate = new AocCoordinate(
                                annotation.year(),
                                annotation.day(),
                                annotation.part());

                        Object instance = clazz.getConstructor().newInstance();
                        Problem<?> problem = (loader) -> {
                            try {
                                return method.invoke(instance, loader);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(String.format("Could not load problem %s", coordinate), e);
                            } catch (InvocationTargetException e) {
                                throw new ProblemExecutionException(
                                        "Execution of problem failed",
                                        e.getTargetException()
                                );
                            }
                        };
                        retval.put(coordinate, problem);
                    }
                }
            }
            return retval;
        } catch (Exception e) {
            return Collections.emptyNavigableMap();
        }
    }


}
