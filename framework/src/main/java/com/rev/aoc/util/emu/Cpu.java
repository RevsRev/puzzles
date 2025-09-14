package com.rev.aoc.util.emu;

import com.rev.aoc.framework.problem.ProblemExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class Cpu {

    private final List<Instruction> program;
    private final Map<String, Long> registers;
    private int index = 0;

    private Cpu(final List<Instruction> program, final Map<String, Long> registers) {
        this.program = program;
        this.registers = registers;
    }

    public static Cpu create(
            final List<String> registerNames,
            final List<String> instructionLines,
            final Function<String, Instruction> lineParser) {

        final Map<String, Long> registers = new HashMap<>();
        registerNames.forEach(name -> registers.put(name, 0L));

        final List<Instruction> instructions = new ArrayList<>(instructionLines.size());
        instructionLines.forEach(line -> instructions.add(lineParser.apply(line)));

        return new Cpu(instructions, registers);
    }

    public void run() {
        while (0 <= index && index < program.size()) {
            index = program.get(index).apply(this);
        }
    }

    public long readRegister(final String register) {
        checkRegister(register);
        return registers.get(register);
    }

    public void writeRegister(final String register, final long value) {
        checkRegister(register);
        registers.put(register, value);
    }

    public int getIndex() {
        return index;
    }

    private void checkRegister(final String register) {
        if (!registers.containsKey(register)) {
            throw new ProblemExecutionException(String.format("FATAL ERROR: Register '%s' does not exist", register));
        }
    }

    public static final class Instruction implements Function<Cpu, Integer> {
        private final Function<Cpu, Integer> func;

        public Instruction(final Function<Cpu, Integer> func) {
            this.func = func;
        }

        @Override
        public Integer apply(final Cpu cpu) {
            return func.apply(cpu);
        }
    }

}
