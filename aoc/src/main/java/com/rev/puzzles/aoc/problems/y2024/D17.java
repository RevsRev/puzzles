package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.aoc.framework.AocVisualisation;
import com.rev.puzzles.framework.framework.io.display.Printer;
import com.rev.puzzles.framework.framework.io.display.format.ColumnFormatter;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.vis.VisualisationException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.function.Consumer;

public final class D17 {

    private static final int DEBUG_LIMIT = 17;

    private static final int FIRST_BIT_FLAG = 1;
    private static final int SECOND_BIT_FLAG = FIRST_BIT_FLAG << 1;
    private static final int THIRD_BIT_FLAG = SECOND_BIT_FLAG << 1;
    private static final int[] CHECK_FLAGS = new int[]{FIRST_BIT_FLAG, SECOND_BIT_FLAG, THIRD_BIT_FLAG};

    private static final int ADV = 0;
    private static final int BXL = 1;
    private static final int BST = 2;
    private static final int JNZ = 3;
    private static final int BXC = 4;
    private static final int OUT = 5;
    private static final int BDV = 6;
    private static final int CDV = 7;
    public static final int UNSOLVED_RETURN_VAL = -1;

    @AocProblemI(year = 2024, day = 17, part = 1)
    public String partOneImpl(final ProblemResourceLoader resourceLoader) {
        Computer comp = loadResourcesAsComputer(resourceLoader);
        final StringBuilder sb = new StringBuilder();
        comp.listener = i -> sb.append(i).append(",");
        comp.start();
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @AocProblemI(year = 2024, day = 17, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader resourceLoader) {
        Computer comp = loadResourcesAsComputer(resourceLoader);
        InputChecker checker = new InputChecker(comp.program);
        checker.check(comp.program.length - 1, 0, 0);
        long solution = checker.getSmallestSolution();
        comp.reset(solution, 0, 0);
        comp.start();
        return checker.getSmallestSolution();
    }

    private static final class InputChecker {
        private final TreeSet<Long> solutions = new TreeSet<>();
        private final int[] program;

        private InputChecker(final int[] program) {
            this.program = program;
        }

        @SuppressWarnings("checkstyle:MagicNumber")
        public long getSmallestSolution() {
            if (solutions.isEmpty()) {
                return -1;
            }
            return solutions.first();
        }

        public void check(final int prgIndex, long regA, long checkedBits) {
            int programIndex = prgIndex;
            if (programIndex == limit()) {
                if (regA < 0) {
                    return; //throw invalid solutions for now
                }
                solutions.add(regA);
                return;
            }

            regA = regA << 3;
            checkedBits = checkedBits << 3;

            TreeSet<Pair<Integer, Integer>> solutionPairs = THREE_BIT_XOR_SOLUTIONS.get(program[programIndex]);
            for (Pair<Integer, Integer> solutionPair : solutionPairs) {
                long regAcpy = regA;
                long checkedBitsCpy = checkedBits;

                int bNot3 = solutionPair.getLeft();
                int b = bNot3 ^ 3;
                int bNot5 = b ^ 5;
                int c = solutionPair.getRight();

                int cShift = bNot5;

                regAcpy |= b;
                checkedBitsCpy |= 7;

                if (!check3bitSegmentMatches(checkedBitsCpy, regAcpy, c, cShift)) {
                    continue;
                }

                regAcpy |= (c << cShift);
                checkedBitsCpy |= (7 << cShift);

                check(programIndex - 1, regAcpy, checkedBitsCpy);
            }
        }

        @SuppressWarnings("checkstyle:MagicNumber")
        private int limit() {
            return Math.max(-1, program.length - 1 - DEBUG_LIMIT);
        }

        private static final Map<Integer, TreeSet<Pair<Integer, Integer>>> THREE_BIT_XOR_SOLUTIONS =
                computeThreeBitXorSolutions();

        private static Map<Integer, TreeSet<Pair<Integer, Integer>>> computeThreeBitXorSolutions() {
            Map<Integer, TreeSet<Pair<Integer, Integer>>> solutions = new HashMap<>();
            for (int i = 0; i < 8; i++) {
                solutions.put(i, computeThreeBitXorSolutions(i));
            }
            return solutions;
        }

        private static TreeSet<Pair<Integer, Integer>> computeThreeBitXorSolutions(int target) {
            TreeSet<Pair<Integer, Integer>> solutions = new TreeSet<>(
                    Comparator.comparingInt(i -> (i.getLeft() ^ 5)));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i ^ j) == target) {
                        solutions.add(Pair.of(i, j));
                    }
                }
            }
            return solutions;
        }

        private boolean check3bitSegmentMatches(long checkBits, long value, long proposed3bitValue, long shift) {
            return check3BitsMatch(checkBits >> shift, proposed3bitValue, value >> shift);
        }

        private boolean check3BitsMatch(long checked, long proposedValue, long value) {
            for (int checkFlag : CHECK_FLAGS) {
                if ((checked & checkFlag) != 0
                        && (proposedValue & checkFlag) != (value & checkFlag)) {
                    return false;
                }
            }
            return true;
        }

    }

    @AocVisualisation(year = 2024, day = 17, part = 1)
    public void visualiseProblem(final ProblemResourceLoader resourceLoader) throws VisualisationException {
        Computer comp = loadResourcesAsComputer(resourceLoader);
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

    private Computer loadResourcesAsComputer(final ProblemResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
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

        private static final ColumnFormatter<Computer>[] COMPUTER_COLS = new ColumnFormatter[]{
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
                        comp -> Long.toString(comp.comboOperand(comp.program[comp.stackPointer + 1]))),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "A", 20, ' ', comp -> Long.toString(comp.registerA)),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "B", 20, ' ', comp -> Long.toString(comp.registerB)),
                new ColumnFormatter.FuncColumnFormatter<Computer>(
                        "C", 20, ' ', comp -> Long.toString(comp.registerC))
        };

        private long registerA;
        private long registerB;
        private long registerC;
        private final int[] program;

        private int stackPointer = 0;
        private Consumer<Long> listener = i -> {
        };
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
                registerA = registerA / (1 << comboOperand(operand));
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
                long output = comboOperand(operand) % 8;
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

        public void reset(long regA, long regB, long regC) {
            registerA = regA;
            registerB = regB;
            registerC = regC;
            stackPointer = 0;
        }

        private void out(long value) {
            listener.accept(value);
        }

        private long comboOperand(int operand) {
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
