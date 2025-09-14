package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class D07 extends AocProblem<Long, Long> {

    @AocProblemI(year = 2024, day = 7, part = 1)
    @Override
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        long[][] targetAndInputs = loadTargetAndInputs(resourceLoader);
        List<BiFunction<Long, Long, Long>> funcs = funcs(false);

        long result = 0;
        for (int i = 0; i < targetAndInputs.length; i++) {
            if (canCreateEquation(targetAndInputs[i], funcs)) {
                result = Math.addExact(result, targetAndInputs[i][0]);
            }
        }
        return result;
    }

    @AocProblemI(year = 2024, day = 7, part = 2)
    @Override
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        long[][] targetAndInputs = loadTargetAndInputs(resourceLoader);
        List<BiFunction<Long, Long, Long>> funcs = funcs(true);

        long result = 0;
        for (int i = 0; i < targetAndInputs.length; i++) {
            if (canCreateEquation(targetAndInputs[i], funcs)) {
                result = Math.addExact(result, targetAndInputs[i][0]);
            }
        }
        return result;
    }

    private boolean canCreateEquation(final long[] targetAndInput, final List<BiFunction<Long, Long, Long>> funcs) {
        long target = targetAndInput[0];
        long result = targetAndInput[1];

        for (int i = 0; i < funcs.size(); i++) {
            if (canCreateEquation(target, result, 2, targetAndInput, funcs, funcs.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean canCreateEquation(final long target,
                                      long result,
                                      int index,
                                      final long[] inputs,
                                      final List<BiFunction<Long, Long, Long>> funcs,
                                      final BiFunction<Long, Long, Long> operation) {
        result = operation.apply(result, inputs[index]);
        index++;
        if (index == inputs.length) {
            if (result == target) {
                return true;
            }
            return false;
        }
        if (result > target) {
            return false;
        }
        for (int i = 0; i < funcs.size(); i++) {
            if (canCreateEquation(target, result, index, inputs, funcs, funcs.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static List<BiFunction<Long, Long, Long>> funcs(final boolean includeConcatenation) {
        List<BiFunction<Long, Long, Long>> funcs = new ArrayList<>();
        funcs.add(multiplyFunction());
        funcs.add(addFunction());
        if (includeConcatenation) {
            funcs.add(concatenationFunction());
        }
        return funcs;
    }

    public static BiFunction<Long, Long, Long> multiplyFunction() {
        return Math::multiplyExact;
    }

    public static BiFunction<Long, Long, Long> addFunction() {
        return Math::addExact;
    }

    public static BiFunction<Long, Long, Long> concatenationFunction() {
        return (i, j) -> {
            String left = Long.toString(i);
            String right = Long.toString(j);
            return Long.parseLong(left + right);
        };
    }

    private long[][] loadTargetAndInputs(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        long[][] retval = new long[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] targetAndInputs = line.split("\\s*:\\s*");
            String[] inputs = targetAndInputs[1].split("\\s+");
            retval[i] = new long[inputs.length + 1];
            retval[i][0] = Long.parseLong(targetAndInputs[0]);
            for (int j = 1; j < inputs.length + 1; j++) {
                retval[i][j] = Long.parseLong(inputs[j - 1]);
            }
        }
        return retval;
    }
}
