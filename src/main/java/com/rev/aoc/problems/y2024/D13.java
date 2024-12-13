package com.rev.aoc.problems.y2024;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;
import com.rev.aoc.util.math.ntheory.diophantine.DiophantineSolver;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class D13 extends AocProblem {
    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 13);
    }

    @Override
    protected long partOneImpl() {
        List<Pair<DiophantineSolver, DiophantineSolver>> equationPairs = loadDiophantineEquations();
        long totalCost = 0;
        for (Pair<DiophantineSolver, DiophantineSolver> eqPair : equationPairs) {
            DiophantineSolver first = eqPair.getLeft();
            DiophantineSolver second = eqPair.getRight();
            if (first.canSolve() && second.canSolve()) {
                totalCost += computeButtonPressesAndScore(first, second);
            }
        }
        return totalCost;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private long computeButtonPressesAndScore(final DiophantineSolver xSolved,
                                              final DiophantineSolver ySolved) {
        BigInteger[] xResult = xSolved.solve().get();
        BigInteger[] yResult = ySolved.solve().get();

        BigInteger xaPresses = xResult[0];
        BigInteger xbPresses = xResult[1];
        BigInteger yaPresses = yResult[0];
        BigInteger ybPresses = yResult[1];

        if (xaPresses.compareTo(BigInteger.valueOf(100L)) > 0L) {
            BigInteger div = xaPresses.divide(xResult[2]);
            xaPresses = xaPresses.subtract(div.multiply(xResult[2]));
            xbPresses = xbPresses.subtract(div.multiply(xResult[3]));
        }
        if (yaPresses.compareTo(BigInteger.valueOf(100L)) > 0L) {
            BigInteger div = yaPresses.divide(yResult[2]);
            yaPresses = yaPresses.subtract(div.multiply(yResult[2]));
            ybPresses = ybPresses.subtract(div.multiply(yResult[3]));
        }

        while (outOfBounds(yaPresses) && !outOfBounds(xaPresses)) {
            yaPresses = yaPresses.add(xResult[3]);
            xaPresses = xaPresses.add(xResult[2]);
        }
        while (outOfBounds(ybPresses) && !outOfBounds(xbPresses)) {
            ybPresses = ybPresses.add(yResult[3]);
            xbPresses = xbPresses.subtract(yResult[2]);
        }

        if (outOfBounds(xaPresses) || outOfBounds(xbPresses) || outOfBounds(yaPresses) || outOfBounds(ybPresses)) {
            return 0;
        }
        return 3 * xaPresses.longValue() + xbPresses.longValue();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private boolean outOfBounds(final BigInteger presses) {
        return presses.compareTo(BigInteger.valueOf(100L)) > 0L || presses.compareTo(BigInteger.ZERO) < 0;
    }

    @Override
    protected long partTwoImpl() {
        return 0;
    }

    private List<Pair<DiophantineSolver, DiophantineSolver>> loadDiophantineEquations() {
        List<String> lines = loadResources();
        List<Pair<DiophantineSolver, DiophantineSolver>> retval = new ArrayList<>(lines.size() / 3);
        for (int i = 0; i < lines.size(); i += 4) {
            String[] aStr = lines.get(i)
                    .replaceAll("\\s+", "")
                    .replaceAll("ButtonA:", "")
                    .replaceAll(".\\+", "")
                    .trim()
                    .split(",");
            String[] bStr = lines.get(i + 1)
                    .replaceAll("\\s+", "")
                    .replaceAll("ButtonB:", "")
                    .replaceAll(".\\+", "")
                    .trim()
                    .split(",");
            String[] cStr = lines.get(i + 2)
                    .replaceAll("\\s+", "")
                    .replaceAll("Prize:", "")
                    .replaceAll(".=", "")
                    .trim()
                    .split(",");
            DiophantineSolver solverOne = new DiophantineSolver(
                    BigInteger.valueOf(Long.parseLong(aStr[0])),
                    BigInteger.valueOf(Long.parseLong(bStr[0])),
                    BigInteger.valueOf(Long.parseLong(cStr[0])));
            DiophantineSolver solverTwo = new DiophantineSolver(
                    BigInteger.valueOf(Long.parseLong(aStr[1])),
                    BigInteger.valueOf(Long.parseLong(bStr[1])),
                    BigInteger.valueOf(Long.parseLong(cStr[1])));
            retval.add(Pair.of(solverOne, solverTwo));
        }
        return retval;
    }

}
