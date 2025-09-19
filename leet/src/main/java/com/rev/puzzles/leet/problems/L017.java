package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class L017 {
    private static Map<Integer, char[]> createDigitToCharsMap() {
        final Map<Integer, char[]> retval = new HashMap<>();

        retval.put(2, new char[]{'a', 'b', 'c'});
        retval.put(3, new char[]{'d', 'e', 'f'});
        retval.put(4, new char[]{'g', 'h', 'i'});
        retval.put(5, new char[]{'j', 'k', 'l'});
        retval.put(6, new char[]{'m', 'n', 'o'});
        retval.put(7, new char[]{'p', 'q', 'r', 's'});
        retval.put(8, new char[]{'t', 'u', 'v'});
        retval.put(9, new char[]{'w', 'x', 'y', 'z'});

        return retval;
    }

    @LeetProblem(number = 17)
    public List<String> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return letterCombinations((String) problemResourceLoader.resources()[0]);
    }

    public List<String> letterCombinations(final String digits) {

        if (digits.isBlank()) {
            return Collections.emptyList();
        }

        final Map<Integer, char[]> digitToCharsMap = createDigitToCharsMap();

        final char[] firstCharacters = digitToCharsMap.get(Integer.parseInt("" + digits.charAt(0)));
        String[] results = new String[firstCharacters.length];
        for (int i = 0; i < firstCharacters.length; i++) {
            results[i] = "" + firstCharacters[i];
        }

        for (int i = 1; i < digits.length(); i++) {
            final char[] characters = digitToCharsMap.get(Integer.parseInt("" + digits.charAt(i)));
            final String[] newResults = new String[results.length * characters.length];
            for (int j = 0; j < results.length; j++) {
                for (int k = 0; k < characters.length; k++) {
                    newResults[k * results.length + j] = results[j] + characters[k];
                }
            }
            results = newResults;
        }
        return List.of(results);
    }


}
