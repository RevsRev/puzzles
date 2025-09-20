package com.rev.puzzles.euler.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PeTestResourceLoader implements ResourceLoader<PeCoordinate> {

    private final Map<PeCoordinate, PeTestData> testData;

    private PeTestResourceLoader(final Map<PeCoordinate, PeTestData> testData) {
        this.testData = testData;
    }

    public static PeTestResourceLoader create(final String packageToSearch) {
        final Map<PeCoordinate, PeTestData> testDataMap = new HashMap<>();
        try {
            final ClassPath cp = ClassPath.from(PeTestResourceLoader.class.getClassLoader());
            final ImmutableSet<ClassPath.ClassInfo> allClasses = cp.getTopLevelClassesRecursive(packageToSearch);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> clazz = classInfo.load();
                for (Method method : clazz.getDeclaredMethods()) {
                    final PeTestData testData = method.getAnnotation(PeTestData.class);
                    final PeProblem problem = method.getAnnotation(PeProblem.class);
                    if (testData != null && problem != null) {
                        final PeCoordinate peCoordinate = new PeCoordinate(problem.number());
                        testDataMap.put(peCoordinate, testData);
                    }
                }
            }
            return new PeTestResourceLoader(testDataMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<PeCoordinate> solutionsWithTests() {
        return testData.keySet();
    }

    public List<String> expectedSolutions(final PeCoordinate peCoordinate) {
        return Arrays.stream(testData.get(peCoordinate).solutions()).toList();
    }

    @Override
    public ProblemResourceLoader<List<String>> getProblemResourceLoader(final PeCoordinate coordinate) {
        return () -> {
            try {
                return Arrays.stream(testData.get(coordinate).inputs()).toList();
            } catch (Exception e) {
                throw new ProblemExecutionException(e.getMessage(), e);
            }
        };
    }
}
