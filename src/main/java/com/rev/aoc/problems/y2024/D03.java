package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.aoc.AocProblem;
import com.rev.aoc.framework.aoc.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class D03 extends AocProblem {

    private static final String MULT_REGEX = "mul\\(\\d+,\\d+\\)|(do\\(\\))|(don't\\(\\))";
    private static final Pattern MULT_PATTERN = Pattern.compile(MULT_REGEX);

    @AocProblemI(year = 2024, day = 3, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        List<Instruction> instructions = loadInstructions(resourceLoader);
        return processInstructions(instructions, true);
    }

    @AocProblemI(year = 2024, day = 3, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<Instruction> instructions = loadInstructions(resourceLoader);
        return processInstructions(instructions, false);
    }

    private static long processInstructions(final List<Instruction> instructions, final boolean mulOnly) {
        long result = 0;
        boolean include = true;
        for (Instruction i : instructions) {
            if (!mulOnly && Type.DO.equals(i.type)) {
                include = true;
            }
            if (!mulOnly && Type.DONT.equals(i.type)) {
                include = false;
            }
            if (include && Type.MUL.equals(i.type)) {
                result += i.first * i.second;
            }
        }
        return result;
    }

    private List<Instruction> loadInstructions(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            Matcher m = MULT_PATTERN.matcher(line);
            while (m.find()) {
                instructions.add(Instruction.factory(m.group()));
            }
        }
        return instructions;
    }

    private enum Type {
        MUL,
        DO,
        DONT
    }

    private static class Instruction {
        private final Type type;
        private final long first;
        private final long second;

        static Instruction factory(final String instructionStr) {
            if (instructionStr.contains("don't()")) {
                return new Instruction(Type.DONT, 0L, 0L);
            }
            if (instructionStr.contains("do()")) {
                return new Instruction(Type.DO, 0L, 0L);
            }

            String[] toks = instructionStr.replace("mul(", "").replace(")", "").split(",");
            return new Instruction(Type.MUL, Long.parseLong(toks[0]), Long.parseLong(toks[1]));
        }

        Instruction(final Type type, final long first, final long second) {
            this.type = type;
            this.first = first;
            this.second = second;
        }
    }
}
