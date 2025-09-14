package com.rev.puzzles.aoc.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.rev.puzzles.framework.framework.ProblemEngine;
import com.rev.puzzles.framework.framework.ProblemLoader;
import com.rev.puzzles.framework.framework.problem.Problem;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

public final class AnnotationProblemLoader<A extends Annotation, C extends ProblemCoordinate<C>>
        implements ProblemLoader<C> {
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.puzzles.aoc.problems";

    private final Class<A> annotationClazz;
    private final Function<A, C> coordinateMapper;

    public AnnotationProblemLoader(
            final Class<A> annotationClazz,
            final Function<A, C> coordinateMapper) {
        this.annotationClazz = annotationClazz;
        this.coordinateMapper = coordinateMapper;
    }


    @Override
    public SortedMap<C, Problem<?>> loadProblemsInRange(final C firstAocCoordinate,
                                                        final C secondAocCoordinate) {
        final NavigableMap<C, Problem<?>> problems = loadProblems();

        if (problems.isEmpty()) {
            //TODO - Probably want some logging here!
            return null;
        }

        C fromKey = firstAocCoordinate;
        C toKey = secondAocCoordinate;
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

    private NavigableMap<C, Problem<?>> loadProblems() {
        try {
            NavigableMap<C, Problem<?>> retval = new TreeMap<>(C::compareTo);
            ClassPath cp = ClassPath.from(ProblemEngine.class.getClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = cp.getTopLevelClassesRecursive(AOC_PROBLEMS_PACKAGE);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> clazz = classInfo.load();
                for (Method method : clazz.getDeclaredMethods()) {
                    final A annotation = method.getAnnotation(annotationClazz);
                    if (annotation != null) {
                        final C apply = coordinateMapper.apply(annotation);

                        Object instance = clazz.getConstructor().newInstance();
                        Problem<?> problem = (loader) -> {
                            try {
                                return method.invoke(instance, loader);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(String.format("Could not load problem %s", apply), e);
                            } catch (InvocationTargetException e) {
                                throw new ProblemExecutionException(
                                        "Execution of problem failed",
                                        e.getTargetException()
                                );
                            }
                        };
                        retval.put(apply, problem);
                    }
                }
            }
            return retval;
        } catch (Exception e) {
            return Collections.emptyNavigableMap();
        }
    }
}
