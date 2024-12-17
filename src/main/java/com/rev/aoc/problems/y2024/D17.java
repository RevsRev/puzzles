package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

import java.util.List;
import java.util.function.Consumer;

public final class D17 extends AocProblem {

    private static final int ADV = 0;
    private static final int BXL = 1;
    private static final int BST = 2;
    private static final int JNZ = 3;
    private static final int BXC = 4;
    private static final int OUT = 5;
    private static final int BDV = 6;
    private static final int CDV = 7;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 7);
    }

    @Override
    protected long partOneImpl() {
        Computer comp = loadResourcesAsComputer();
        return 0;
    }

    @Override
    protected long partTwoImpl() {
        return 0;
    }

    private Computer loadResourcesAsComputer() {
        List<String> lines = loadResources();
        int registerA = Integer.parseInt(lines.get(0)
                .trim().replace("\\s+", "").replace("RegisterA:", "").toString());
        int registerB = Integer.parseInt(lines.get(1)
                .trim().replace("\\s+", "").replace("RegisterB:", "").toString());
        int registerC = Integer.parseInt(lines.get(2)
                .trim().replace("\\s+", "").replace("RegisterC:", "").toString());

        String[] programStrs = lines.get(3)
                .replace("\\s+", "").replace("Program:", "").split(",");
        int[] program = new int[programStrs.length];
        for (int i = 0; i < program.length; i++) {
            program[i] = Integer.parseInt(programStrs[i]);
        }
        return new Computer(registerA, registerB, registerC, program);
    }

    private static final class Computer {
        private int registerA;
        private int registerB;
        private int registerC;
        private final int[] program;

        private int stackPointer = 0;
        private Consumer<Integer> listener = i -> { };

        private Computer(int registerA,
                         int registerB,
                         int registerC,
                         final int[] program) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.program = program;
        }

        private void start() {
            while (stackPointer < program.length) {
                int instructionCode = program[stackPointer];
                int operand = program[stackPointer + 1];
                execute(instructionCode, operand);
            }
        }
        private void execute(final int instructionCode, final int operand) {
            if (instructionCode == ADV) {
                registerA = registerA /  (1 << comboOperand(operand));
                stackPointer += 2;
                return;
            }
            if (instructionCode == BXL) {
                registerB = registerB ^ operand;
                stackPointer += 2;
                return;
            }
            if (instructionCode == BST) {
                registerB = (comboOperand(operand) % 8) & 7;
                stackPointer += 2;
                return;
            }
            if (instructionCode == JNZ) {
                if (registerA == 0) {
                    stackPointer += 2;
                    return;
                }
                stackPointer = operand;
                return;
            }
            if (instructionCode == BXC) {
                registerB = registerB ^ registerC;
                stackPointer += 2;
                return;
            }
            if (instructionCode == OUT) {
                int output = comboOperand(operand) % 8;
                out(output);
                stackPointer += 2;
                return;
            }
            if (instructionCode == BDV) {
                registerB = registerB /  (1 << comboOperand(operand));
                stackPointer += 2;
                return;
            }
            if (instructionCode == CDV) {
                registerC = registerC /  (1 << comboOperand(operand));
                stackPointer += 2;
            }
        }

        private void out(int value) {
            listener.accept(value);
        }

        private int comboOperand(int operand) {
            if (operand <= 3) {
                return operand;
            }
            if (operand == 4) {
                return registerA;
            }
            if (operand == 5) {
                return registerB;
            }
            if (operand == 6) {
                return registerC;
            }
            throw new RuntimeException("Invalid combo operand");
        }
    }
}
