package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.display.Printer;
import com.rev.aoc.framework.io.display.format.ColumnFormatter;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.math.ntheory.util.Pow;
import com.rev.aoc.vis.VisualisationException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
    public static final int UNSOLVED_RETURN_VAL = -1;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 17);
    }

    @Override
    protected long partOneImpl() {
        Computer comp = loadResourcesAsComputer();
        final StringBuilder sb = new StringBuilder();
        comp.listener = i -> sb.append(i).append(",");
        comp.start();
//        sb.toString(); TODO - CHANGE FRAMEWORK TO ALLOW FOR DIFFERENT TYPES OF RETURN VALUE
        return UNSOLVED_RETURN_VAL;
    }

    @Override
    protected long partTwoImpl() {
        Computer comp = loadResourcesAsComputer();
        InputChecker checker = new InputChecker(comp.program);
        checker.check(0);
        comp.setPrettyPrint();
        comp.registerA = (int) checker.regA;
        final StringBuilder sb = new StringBuilder();
        comp.listener = i -> sb.append(i).append(",");
        comp.start();
        System.out.print(Arrays.toString(comp.program));
        System.out.println(sb);
        return checker.regA;
    }

    private static final class InputChecker {
        private long regA = 0;
        private long checkedBits = 0;
        private final int[] program;

        private InputChecker(final int[] program) {
            this.program = program;
        }

        private boolean check(final int prgIndex) {
            int programIndex = prgIndex;
            if (programIndex == program.length) {
                return true;
            }

            int shift = 3 * programIndex;
            for (long candidateC = 0; candidateC < 8; candidateC++) {
                final long bNot3 = program[programIndex] ^ candidateC;
                final long b = bNot3 ^ 3;
                final long bNot5 = b ^ 5;
                final long checkedBNot5 = (regA >> (bNot5 + shift)) % 8;
                if (checkedBNot5 != 0) {
                    long compare = (regA >> (bNot5 + shift)) % 8;
                    if (compare != candidateC) {
                        continue;
                    }
                }
                regA |= (candidateC << (bNot5 + shift));
                checkedBits |= (7 << (bNot5 + shift));

                long checkedB = (checkedBits >> shift) % 8;
                if (checkedB != 0) {
                    long compare = (regA >> shift) & 8;
                    if ((checkedB & b) != (checkedB & compare)) {
                        clearAbove(shift);
                        continue;
                    }
                }

                checkedBits |= (7 << shift);
                regA |= b;

                if (check(programIndex + 1)) {
                    return true;
                }
                clearAbove(shift);
            }
            return false;
        }

        private void clearAbove(int shift) {
            long clearFlags = (Long.MAX_VALUE >> shift);
            checkedBits &= clearFlags;
            regA &= clearFlags;
        }

    }

    @Override
    public void visualiseProblem() throws VisualisationException {
        Computer comp = loadResourcesAsComputer();
        Scanner scanner = new Scanner(System.in);
        String next = null;
        while (next == null) {
            System.out.println("Enter initial value of register A: ");
            next = scanner.next();
            try {
                int regA = Integer.parseInt(next);
                comp.registerA = regA;
            } catch (NumberFormatException e) {
                System.out.println("Value must be a valid 32 bit signed integer.");
            }
        }
        comp.setPrettyPrint();
        final StringBuilder sb = new StringBuilder();
        comp.listener = i -> sb.append(i).append(",");
        comp.start();
        System.out.println();
        System.out.println(sb);
    }

    private long getTarget(final int[] program) {
        long target = 0;
        for (int value : program) {
            target = target << 3;
            target |= value;
        }
        return target;
    }

    private Computer loadResourcesAsComputer() {
        List<String> lines = loadResources();
        int registerA = Integer.parseInt(lines.get(0)
                .trim().replaceAll("\\s+", "").replaceAll("RegisterA:", ""));
        int registerB = Integer.parseInt(lines.get(1)
                .trim().replaceAll("\\s+", "").replaceAll("RegisterB:", ""));
        int registerC = Integer.parseInt(lines.get(2)
                .trim().replaceAll("\\s+", "").replaceAll("RegisterC:", ""));

        String[] programStrs = lines.get(4)
                .replaceAll("\\s+", "").replaceAll("Program:", "").split(",");
        int[] program = new int[programStrs.length];
        for (int i = 0; i < program.length; i++) {
            program[i] = Integer.parseInt(programStrs[i]);
        }
        return new Computer(registerA, registerB, registerC, program);
    }

    private static final class Computer {

        private static final ColumnFormatter<Computer>[] COMPUTER_COLS = new ColumnFormatter[] {
            new ColumnFormatter.FuncColumnFormatter<Computer>(
                    "SP", 20, ' ', comp -> Integer.toString(comp.stackPointer)),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "INS", 20, ' ',
                        comp -> Integer.toString(comp.program[comp.stackPointer])),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "OP", 20, ' ',
                        comp -> Integer.toString(comp.program[comp.stackPointer + 1])),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "COMBO", 20, ' ',
                        comp -> Integer.toString(comp.comboOperand(comp.program[comp.stackPointer + 1]))),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "A", 20, ' ', comp -> Integer.toString(comp.registerA)),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "B", 20, ' ', comp -> Integer.toString(comp.registerB)),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "C", 20, ' ', comp -> Integer.toString(comp.registerC))
        };

        private int registerA;
        private int registerB;
        private int registerC;
        private final int[] program;

        private int stackPointer = 0;
        private Consumer<Integer> listener = i -> { };
        private boolean print = false;
        private Printer<Computer> printer = new Printer<>(COMPUTER_COLS);

        private Computer(int registerA,
                         int registerB,
                         int registerC,
                         final int[] program) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.program = program;
        }

        public void start() {
            while (stackPointer < program.length) {
                if (print) {
                    printer.printResult(this);
                }
                int opCode = program[stackPointer];
                int operand = program[stackPointer + 1];
                execute(opCode, operand);
            }
            if (print) {
                printer.printSeparator();
            }
        }

        public void setPrettyPrint() {
            print = true;
        }

        private void execute(final int opCode, final int operand) {
            if (opCode == ADV) {
                registerA = registerA /  (1 << comboOperand(operand));
                stackPointer += 2;
                return;
            }
            if (opCode == BXL) {
                registerB = (registerB ^ operand % 8);
                stackPointer += 2;
                return;
            }
            if (opCode == BST) {
                registerB = comboOperand(operand) % 8;
                stackPointer += 2;
                return;
            }
            if (opCode == JNZ) {
                if (registerA == 0) {
                    stackPointer += 2;
                    return;
                }
                stackPointer = operand;
                return;
            }
            if (opCode == BXC) {
                registerB = (registerB ^ registerC) % 8;
                stackPointer += 2;
                return;
            }
            if (opCode == OUT) {
                int output = comboOperand(operand) % 8;
                out(output);
                stackPointer += 2;
                return;
            }
            if (opCode == BDV) {
                registerB = (registerA / (1 << comboOperand(operand))) % 8;
                stackPointer += 2;
                return;
            }
            if (opCode == CDV) {
                registerC = (registerA / (1 << comboOperand(operand)) % 8);
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
