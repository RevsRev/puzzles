package com.rev.aoc.problems.y2023;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D02 extends AocProblem<Long, Long> {

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2023, 2);
    }

    @AocProblemI(year = 2023, day = 2, part = 1)
    @Override
    protected Long partOneImpl(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();

        Map<Integer, List<Handful>> gameHandfulMap = parseToGameMap(lines);
        Iterator<Integer> itGameNumbers = gameHandfulMap.keySet().iterator();
        long result = 0;
        while (itGameNumbers.hasNext()) {
            int gameNumber = itGameNumbers.next();
            result += getIncrementingAmountPartOne(gameHandfulMap, gameNumber);
        }
        return result;
    }

    @AocProblemI(year = 2023, day = 2, part = 2)
    @Override
    protected Long partTwoImpl(final ResourceLoader resourceLoader) {
        return 0L;
    }

    Map<Integer, List<Handful>> parseToGameMap(final List<String> lines) {
        Map<Integer, List<Handful>> retval = new HashMap<>();
        Iterator<String> itLines = lines.iterator();
        while (itLines.hasNext()) {
            String line = itLines.next();
            parseFromLine(retval, line);
        }
        return retval;
    }

    private void parseFromLine(final Map<Integer, List<Handful>> retval, final String line) {
        String[] gameAndValues = line.split(":");
        String game = gameAndValues[0];
        String[] gameHandfuls = gameAndValues[1].split(";");

        int gameNumber = Integer.parseInt(game.replaceFirst("Game ", ""));
        List<Handful> handfuls = new ArrayList<>();
        for (int i = 0; i < gameHandfuls.length; i++) {
            String gameHandful = gameHandfuls[i];
            String[] colors = gameHandful.split(",");
            handfuls.add(Handful.from(colors));
        }
        retval.put(gameNumber, handfuls);
    }

    /**
     * PART ONE
     */
    private static final int NUM_REDS = 12;
    private static final int NUM_GREENS = 13;
    private static final int NUM_BLUES = 14;

    private long getIncrementingAmountPartOne(final Map<Integer, List<Handful>> gameHandfulMap, final int gameNumber) {
        List<Handful> handfuls = gameHandfulMap.get(gameNumber);
        if (checkHandfuls(handfuls)) {
            return gameNumber;
        }
        return 0;
    }

    private boolean checkHandfuls(final List<Handful> handfuls) {
        for (int i = 0; i < handfuls.size(); i++) {
            Handful handful = handfuls.get(i);
            if (handful.red > NUM_REDS) {
                return false;
            }
            if (handful.blue > NUM_BLUES) {
                return false;
            }
            if (handful.green > NUM_GREENS) {
                return false;
            }
        }
        return true;
    }

    /**
     * PART TWO
     */

    private static final class Handful {
        private long red = 0;
        private long green = 0;
        private long blue = 0;

        private static final String RED = " red";
        private static final String GREEN = " green";
        private static final String BLUE = " blue";

        public static Handful from(final String[] colors) {
            Handful handful = new Handful();
            for (int i = 0; i < colors.length; i++) {
                String colorAndAmount = colors[i].trim();
                if (colorAndAmount.contains(RED)) {
                    handful.red = Integer.parseInt(colorAndAmount.replaceAll(RED, ""));
                } else if (colorAndAmount.contains(GREEN)) {
                    handful.green = Integer.parseInt(colorAndAmount.replaceAll(GREEN, ""));
                } else if (colorAndAmount.contains(BLUE)) {
                    handful.blue = Integer.parseInt(colorAndAmount.replaceAll(BLUE, ""));
                } else {
                    throw new InvalidParameterException();
                }
            }
            return handful;
        }
    }
}
