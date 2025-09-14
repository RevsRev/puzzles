package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.aoc.AocProblem;
import com.rev.aoc.framework.aoc.AocProblemI;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D08 extends AocProblem {

    private static final Pattern ESCAPE = Pattern.compile("\\\\");

    @AocProblemI(year = 2015, day = 8, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        final List<String> lines = resourceLoader.resources();
        long total = 0;
        for (String s : lines) {
            final long literalLength = s.length();
            final long characterLength = charLength(s);
            total += (literalLength - characterLength);
        }
        return total;
    }

    @AocProblemI(year = 2015, day = 8, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        final List<String> lines = resourceLoader.resources();
        long total = 0;
        for (String s : lines) {
            final long literalLength = s.length();
            final long encodedLength = encode(s).length() + 2; //include surrounding double quotes!
            total += encodedLength - literalLength;
        }
        return total;
    }

    private long charLength(final String s) {
        long count = 0;
        if (s.charAt(0) == '"') {
            count++;
        }
        if (s.charAt(s.length() - 1) == '"') {
            count++;
        }

        Matcher matcher = ESCAPE.matcher(s);
        final List<MatchResult> matchResults = new ArrayList<>(matcher.results().toList());

        for (int i = 0; i < matchResults.size(); i++) {
            final MatchResult matchResult = matchResults.get(i);
            final int end = matchResult.end();
            if (s.charAt(end) == '\\') {
                i++;
                count++;
            } else if (s.charAt(end) == '"') {
                count++;
            } else {
                count += 3;
            }
        }
        return s.length() - count;
    }

    private String encode(final String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
