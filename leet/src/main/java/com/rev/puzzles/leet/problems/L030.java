package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class L030 {
    @LeetProblem(number = 30)
    public List<Integer> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return findSubstring((String) problemResourceLoader.resources()[0],
                (String[]) problemResourceLoader.resources()[1]);
    }

    public List<Integer> findSubstring(final String s, final String[] words) {
        final int wordLength = words[0].length();
        final int substringLength = wordLength * words.length;

        if (s.length() < substringLength) {
            return Collections.emptyList();
        }

        final List<Integer> substringIndices = new ArrayList<>();
        for (int k = 0; k < wordLength; k++) {
            substringIndices.addAll(findSubstringUsingTokens(s.substring(k), words, k));
        }
        return substringIndices;
    }


    // By the way, this approach is called a "sliding window"
    private List<Integer> findSubstringUsingTokens(final String s, final String[] words, final int startOffset) {
        final int wordLength = words[0].length();
        final int substringLength = wordLength * words.length;
        Map<String, AtomicInteger> wordCounts = wordCounts(words);

        final List<Integer> substringIndices = new ArrayList<>();

        int currentRunLength = 0;
        boolean wordsRequireReset = false;
        for (int i = 0; i <= s.length() - wordLength; i += wordLength) {
            final String token = s.substring(i, i + wordLength);

            if (wordCounts.containsKey(token)) {
                if (wordCounts.get(token).get() <= 0) {
                    int substringIndex = i - currentRunLength;
                    do {
                        wordCounts.get(s.substring(substringIndex, substringIndex + wordLength)).getAndIncrement();
                        currentRunLength -= wordLength;
                        substringIndex += wordLength;
                    } while (wordCounts.get(token).get() == 0);
                }
                currentRunLength += wordLength;
                wordsRequireReset = true;
                wordCounts.get(token).getAndDecrement();
            } else {
                if (wordsRequireReset) {
                    wordCounts = wordCounts(words);
                    wordsRequireReset = false;
                }
                currentRunLength = 0;
            }

            if (currentRunLength == substringLength) {
                final int substringIndex = i + wordLength - currentRunLength;
                substringIndices.add(startOffset + substringIndex);
                wordCounts.get(s.substring(substringIndex, substringIndex + wordLength)).getAndIncrement();
                currentRunLength -= wordLength;
            }
        }
        return substringIndices;
    }

    private Map<String, AtomicInteger> wordCounts(final String[] words) {
        final Map<String, AtomicInteger> retval = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            retval.computeIfAbsent(words[i], (k) -> new AtomicInteger(0)).getAndIncrement();
        }
        return retval;
    }
}
