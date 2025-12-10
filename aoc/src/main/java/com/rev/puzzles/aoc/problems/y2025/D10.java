package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public final class D10 {

    @AocProblemI(year = 2025, day = 10, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        Map<char[], List<Set<Integer>>> statesAndButtons = new HashMap<>();
        resourceLoader.resources()
                .stream().forEach(
                        s -> {
                            final String[] split = s.split("\\s+");
                            final char[] lights = split[0]
                                    .replaceAll("\\[", "")
                                    .replaceAll("]", "")
                                    .toCharArray();

                            List<Set<Integer>> buttonsList = new ArrayList<>();
                            for (int i = 1; i < split.length -1; i++) {
                                final String[] buttons = split[i].replaceAll("\\(", "").replaceAll("\\)", "").split(",");
                                final Set<Integer> buttonsSet = Arrays.stream(buttons).map(but -> Integer.parseInt(but))
                                        .collect(Collectors.toSet());
                                buttonsList.add(buttonsSet);
                            }
                            statesAndButtons.put(lights, buttonsList);
                        }
                );

        long totalPresses = 0;
        for (final Map.Entry<char[], List<Set<Integer>>> stateAndButtons : statesAndButtons.entrySet()) {
            totalPresses += computeNumPressesToTurnOn(stateAndButtons.getKey(), stateAndButtons.getValue());
        }
        return totalPresses;
    }

    @AocProblemI(year = 2025, day = 10, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        Map<int[], List<Set<Integer>>> joltagesAndButtons = new HashMap<>();
        resourceLoader.resources()
                .stream().forEach(
                        s -> {
                            final String[] split = s.split("\\s+");
                            final String joltagesStr = split[split.length - 1]
                                    .replaceAll("\\{", "")
                                    .replaceAll("}", "");
                            final int[] joltages =
                                    Arrays.stream(joltagesStr.split(",")).map(Integer::parseInt).mapToInt(i -> i).toArray();

                            List<Set<Integer>> buttonsList = new ArrayList<>();
                            for (int i = 1; i < split.length -1; i++) {
                                final String[] buttons = split[i].replaceAll("\\(", "").replaceAll("\\)", "").split(",");
                                final Set<Integer> buttonsSet = Arrays.stream(buttons).map(but -> Integer.parseInt(but))
                                        .collect(Collectors.toSet());
                                buttonsList.add(buttonsSet);
                            }
                            joltagesAndButtons.put(joltages, buttonsList);
                        }
                );

        long totalPresses = 0;
        for (final Map.Entry<int[], List<Set<Integer>>> joltageAndButtons : joltagesAndButtons.entrySet()) {
            System.out.println("Computing joltages for: " + Arrays.toString(joltageAndButtons.getKey()));
            totalPresses += computeNumPressesToConfigureJoltage(joltageAndButtons.getKey(), joltageAndButtons.getValue());
        }
        return totalPresses;
    }

    private long computeNumPressesToConfigureJoltage(final int[] targetJoltage, final List<Set<Integer>> buttons) {
        final Queue<List<Set<Integer>>> attempts = new ArrayDeque<>();
        buttons.forEach(b -> {
            attempts.add(List.of(b));
        });

        while (!attempts.isEmpty()) {

            final List<Set<Integer>> pop = attempts.poll();
            int[] startState = new int[targetJoltage.length];
            for (int i = 0; i < targetJoltage.length; i++) {
                startState[i] = targetJoltage[i];
            }

            boolean solutionFound = true;
            for (final Set<Integer> integers : pop) {
                for (final int lightIndex : integers) {
                    startState[lightIndex] -= 1;
                    if (startState[lightIndex] > targetJoltage[lightIndex]) {
                        solutionFound = false;
                        break;
                    }
                }
                if (!solutionFound) {
                    break;
                }
            }


            // early exit
            if (!solutionFound) {
                for (final Set<Integer> button : buttons) {
                    final List<Set<Integer>> nextButtons = new ArrayList<>(pop);
                    nextButtons.add(button);
                    attempts.add(nextButtons);
                }
                continue;
            }

            for (int i = 0; i < targetJoltage.length; i++) {
                if (startState[i] != targetJoltage[i]) {
                    solutionFound = false;
                    break;
                }
            }

            if (solutionFound) {
                return pop.size();
            }

            for (final Set<Integer> button : buttons) {
                final List<Set<Integer>> nextButtons = new ArrayList<>(pop);
                nextButtons.add(button);
                attempts.add(nextButtons);
            }
        }
        throw new ProblemExecutionException("Could not solve problem!");
    }

    private long computeNumPressesToTurnOn(final char[] target, final List<Set<Integer>> buttons) {
        final Queue<List<Set<Integer>>> attempts = new ArrayDeque<>();
        buttons.forEach(b -> {
            attempts.add(List.of(b));
        });

        while (!attempts.isEmpty()) {

            final List<Set<Integer>> pop = attempts.poll();
            char[] startState = new char[target.length];
            for (int i = 0; i < target.length; i++) {
                startState[i] = '.';
            }

            for (final Set<Integer> integers : pop) {
                for (final int lightIndex : integers) {
                    startState[lightIndex] = startState[lightIndex] == '.' ? '#' : '.';
                }
            }

            boolean solutionFound = true;
            for (int i = 0; i < target.length; i++) {
                if (startState[i] != target[i]) {
                    solutionFound = false;
                    break;
                }
            }

            if (solutionFound) {
                return pop.size();
            }

            for (final Set<Integer> button : buttons) {
                final List<Set<Integer>> nextButtons = new ArrayList<>(pop);
                nextButtons.add(button);
                attempts.add(nextButtons);
            }
        }
        throw new ProblemExecutionException("Could not solve problem!");
    }
}
