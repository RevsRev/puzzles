package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class D19 extends AocProblem<Integer, Integer> {

    private static final Map<String, Pattern> PATTERN_CACHE = new HashMap<>();
    public static final String START_ELECTRON = "e";
    public static final int NOT_FOUND = -1;

    @AocProblemI(year = 2015, day = 19, part = 1)
    @Override
    public Integer partOneImpl(final ResourceLoader resourceLoader) {
        Pair<String, Map<String, Set<String>>> inputAndReplacements = inputAndReplacements(resourceLoader);
        final String molecule = inputAndReplacements.getLeft();
        final Map<String, Set<String>> replacements = inputAndReplacements.getRight();

        final Set<String> products = new HashSet<>();

        for (Map.Entry<String, Set<String>> strAndReplacement : replacements.entrySet()) {
            final String key = strAndReplacement.getKey();
            final Set<String> replacement = strAndReplacement.getValue();
            final Pattern p = PATTERN_CACHE.computeIfAbsent(key, Pattern::compile);
            final Matcher m = p.matcher(molecule);
            m.results().forEach(result -> {
                final int start = result.start();
                final int end = result.end();
                for (String r : replacement) {
                    products.add(substitue(molecule, r, start, end));
                }
            });
        }
        return products.size();
    }

    private static String substitue(final String molecule, final String r, final int start, final int end) {
        final StringBuilder sb = new StringBuilder();
        if (start > 0) {
            sb.append(molecule.substring(0, start));
        }
        sb.append(r);
        if (end < molecule.length()) {
            sb.append(molecule.substring(end));
        }
        return sb.toString();
    }

    @AocProblemI(year = 2015, day = 19, part = 2)
    @Override
    public Integer partTwoImpl(final ResourceLoader resourceLoader) {
        final Pair<String, Map<String, Set<String>>> inputsAndReplacements = inputAndReplacements(resourceLoader);
        final String molecule = inputsAndReplacements.getLeft();
        final Map<String, Set<String>> formulae = inputsAndReplacements.getRight();

        final TreeMap<Integer, Map<String, Set<String>>> invertedFormulae = new TreeMap<>(Comparator.reverseOrder());
        formulae.forEach((input, outputs) -> {
            outputs.forEach(output -> {
                invertedFormulae
                        .computeIfAbsent(output.length(), k -> new HashMap<>())
                        .computeIfAbsent(output, k -> new HashSet<>())
                        .add(input);
            });
        });

        return solveWithBacktracking(molecule, invertedFormulae, 0);
    }

    private Integer solveWithBacktracking(
            final String molecule,
            final TreeMap<Integer, Map<String, Set<String>>> invertedFormulaeBySize,
            final int depth) {
        if (START_ELECTRON.equals(molecule)) {
            return depth;
        }

        //It's not pretty, but it works
        for (final Map<String, Set<String>> invertedFormulae : invertedFormulaeBySize.values()) {
            for (Map.Entry<String, Set<String>> invertedFormula : invertedFormulae.entrySet()) {
                final String products = invertedFormula.getKey();
                final Pattern p = PATTERN_CACHE.computeIfAbsent(products, Pattern::compile);
                List<MatchResult> matchResults = p.matcher(molecule).results().toList();
                if (!matchResults.isEmpty()) {
                    final Set<String> substitutions = invertedFormula.getValue();
                    for (final MatchResult m : matchResults) {
                        for (final String substitution : substitutions) {
                            final int result = solveWithBacktracking(
                                    substitue(molecule, substitution, m.start(), m.end()),
                                    invertedFormulaeBySize,
                                    depth + 1
                            );
                            if (result != NOT_FOUND) {
                                return result;
                            }
                        }
                    }
                }
            }
        }
        return NOT_FOUND;
    }

    private Pair<String, Map<String, Set<String>>> inputAndReplacements(final ResourceLoader resourceLoader) {
        final List<String> lines = resourceLoader.resources();

        final Map<String, Set<String>> replacements = new HashMap<>();

        int index = 0;
        while (!lines.get(index).isEmpty()) {
            String[] split = lines.get(index).replaceAll("\\s", "").split("=>");
            replacements.computeIfAbsent(split[0], k -> new HashSet<>()).add(split[1]);
            index++;
        }

        index++;
        return Pair.of(lines.get(index), replacements);
    }
}
