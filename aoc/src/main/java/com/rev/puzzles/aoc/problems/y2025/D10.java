package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;

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
        resourceLoader.resources().stream().forEach(s -> {
            final String[] split = s.split("\\s+");
            final char[] lights = split[0].replaceAll("\\[", "").replaceAll("]", "").toCharArray();

            List<Set<Integer>> buttonsList = new ArrayList<>();
            for (int i = 1; i < split.length - 1; i++) {
                final String[] buttons = split[i].replaceAll("\\(", "").replaceAll("\\)", "").split(",");
                final Set<Integer> buttonsSet =
                        Arrays.stream(buttons).map(but -> Integer.parseInt(but)).collect(Collectors.toSet());
                buttonsList.add(buttonsSet);
            }
            statesAndButtons.put(lights, buttonsList);
        });

        long totalPresses = 0;
        for (final Map.Entry<char[], List<Set<Integer>>> stateAndButtons : statesAndButtons.entrySet()) {
            totalPresses += computeNumPressesToTurnOn(stateAndButtons.getKey(), stateAndButtons.getValue());
        }
        return totalPresses;
    }

    @AocProblemI(year = 2025, day = 10, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        Map<int[], List<Set<Integer>>> joltagesAndButtons = new HashMap<>();
        resourceLoader.resources().stream().forEach(s -> {
            final String[] split = s.split("\\s+");
            final String joltagesStr = split[split.length - 1].replaceAll("\\{", "").replaceAll("}", "");
            final int[] joltages =
                    Arrays.stream(joltagesStr.split(",")).map(Integer::parseInt).mapToInt(i -> i).toArray();

            List<Set<Integer>> buttonsList = new ArrayList<>();
            for (int i = 1; i < split.length - 1; i++) {
                final String[] buttons = split[i].replaceAll("\\(", "").replaceAll("\\)", "").split(",");
                final Set<Integer> buttonsSet =
                        Arrays.stream(buttons).map(but -> Integer.parseInt(but)).collect(Collectors.toSet());
                buttonsList.add(buttonsSet);
            }
            joltagesAndButtons.put(joltages, buttonsList);
        });

        long totalPresses = 0;
        for (final Map.Entry<int[], List<Set<Integer>>> joltageAndButtons : joltagesAndButtons.entrySet()) {
            System.out.println("Computing joltages for: " + Arrays.toString(joltageAndButtons.getKey()));
            totalPresses +=
                    computeNumPressesToConfigureJoltage(joltageAndButtons.getKey(), joltageAndButtons.getValue());
        }
        return totalPresses;
    }

    private long computeNumPressesToConfigureJoltage(final int[] targetJoltage, final List<Set<Integer>> buttons) {
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        Variable[] variables = new Variable[buttons.size()];
        for (int i = 0; i < variables.length; i++) {
            variables[i] = model.addVariable("b" + i).lower(0).integer(true);
        }

        Expression objective = model.addExpression("obj").weight(1);
        for (int i = 0; i < variables.length; i++) {
            objective.set(variables[i], 1);
        }

        for (int j = 0; j < targetJoltage.length; j++) {
            Expression e = model.addExpression("j" + j).level(targetJoltage[j]);
            for (int b = 0; b < buttons.size(); b++) {
                if (buttons.get(b).contains(j)) {
                    e.set(variables[b], 1);
                }
            }
        }

        final Optimisation.Result result = model.minimise();

        if (!result.getState().isFeasible()) {
            throw new ProblemExecutionException("Infeasible!");
        }

        long sum = 0;
        for (int i = 0; i < result.size(); i++) {
            sum += result.longValue(i);
        }
        return sum;
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
                if (!nextButtons.contains(button)) {
                    nextButtons.add(button);
                    attempts.add(nextButtons);
                }
            }
        }
        throw new ProblemExecutionException("Could not solve problem!");
    }
}
