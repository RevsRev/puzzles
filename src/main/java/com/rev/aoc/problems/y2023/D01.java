package com.rev.aoc.problems.y2023;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class D01 extends AocProblem {

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2023, 1);
    }

    @Override
    protected long partOneImpl() {
        return solve(this::formatPartOne);
    }

    @Override
    protected long partTwoImpl() {
        return solve(this::formatPartTwo);
    }

    private long solve(final Function<String, String> formatter) {
        List<String> trebLines = loadResources();
        long calibrationSum = 0;
        for (int i = 0; i < trebLines.size(); i++) {
            String line = trebLines.get(i);
            calibrationSum += getCalibrationNumber(line, formatter);
        }
        return calibrationSum;
    }

    private long getCalibrationNumber(final String line, final Function<String, String> formatter) {
        String formattedLine = formatter.apply(line);
        StringBuilder sbConfigurationNumber = new StringBuilder();
        for (int i = 0; i < formattedLine.length(); i++) {
            char ch = formattedLine.charAt(i);
            if (Character.isDigit(ch)) {
                sbConfigurationNumber.append(ch);
                break;
            }
        }

        for (int i = formattedLine.length() - 1; i >= 0; i--) {
            char ch = formattedLine.charAt(i);
            if (Character.isDigit(ch)) {
                sbConfigurationNumber.append(ch);
                break;
            }
        }
        return Long.parseLong(sbConfigurationNumber.toString());
    }

    /**
     * PART ONE
     */
    private String formatPartOne(final String line) {
        return line;
    }

    /**
     * PART TWO
     */
    private static final Map<String, String> NUMS_AS_WORDS =
            Map.of(
                    "one", "1",
                    "two", "2",
                    "three", "3",
                    "four", "4",
                    "five", "5",
                    "six", "6",
                    "seven", "7",
                    "eight", "8",
                    "nine", "9");
    private static final Map<String, String> NUMS_AS_WORDS_REVERSED = getNumsAsWordsReversed();

    protected String formatPartTwo(final String line) {
        String forwardLine = formatForwards(line, NUMS_AS_WORDS);
        String backwardLine =
                formatForwards(
                        new StringBuilder().append(line).reverse().toString(), NUMS_AS_WORDS_REVERSED);

        return forwardLine + (new StringBuilder().append(backwardLine).reverse().toString());
    }

    private String formatForwards(final String line, final Map<String, String> words) {
        String earliestReplacement = "";
        String retval = line;
        do {
            earliestReplacement = "";
            long earliestIndex = retval.length();
            Iterator<String> it = words.keySet().iterator();
            while (it.hasNext()) {
                String word = it.next();
                int index = retval.indexOf(word);
                if (index >= 0 && index < earliestIndex) {
                    earliestIndex = index;
                    earliestReplacement = word;
                }
            }
            if (!earliestReplacement.equals("")) {
                retval = retval.replaceFirst(earliestReplacement, words.get(earliestReplacement));
            }
        } while (!earliestReplacement.equals(""));
        return retval;
    }

    private static Map<String, String> getNumsAsWordsReversed() {
        Map<String, String> retval = new HashMap<>();
        Iterator<String> it = NUMS_AS_WORDS.keySet().iterator();
        while (it.hasNext()) {
            String word = it.next();
            String numeral = NUMS_AS_WORDS.get(word);
            String wordReversed = new StringBuilder().append(word).reverse().toString();
            retval.put(wordReversed, numeral);
        }
        return retval;
    }
}
