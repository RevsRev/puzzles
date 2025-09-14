package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.framework.framework.problem.ResourceLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class D16 {

    private static final long UNKNOWN = -1;
    private static final Pattern SUE_REGEX = Pattern.compile("^Sue \\d+:");

    @AocProblemI(year = 2015, day = 16, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        final BiFunction<Sue, Sue, Boolean> matcher = partOneSueMatcher();
        return findSue(resourceLoader, matcher);
    }

    @AocProblemI(year = 2015, day = 16, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        final BiFunction<Sue, Sue, Boolean> matcher = partTwoMatcher();
        return findSue(resourceLoader, matcher);
    }

    private long findSue(final ResourceLoader resourceLoader, final BiFunction<Sue, Sue, Boolean> matcher) {
        final List<Sue> sues = loadIndexedIdentifiers(resourceLoader);

        final Sue key = new Sue();
        key.children = 3;
        key.cats = 7;
        key.samoyeds = 2;
        key.pomeranians = 3;
        key.akitas = 0;
        key.vizslas = 0;
        key.goldfish = 5;
        key.trees = 3;
        key.cars = 2;
        key.perfumes = 1;

        final Set<Sue> potentialMatches = new HashSet<>();
        for (final Sue sue : sues) {
            if (matcher.apply(key, sue)) {
                potentialMatches.add(sue);
            }
        }

        if (potentialMatches.size() != 1) {
            potentialMatches.forEach(System.out::println);
            throw new ProblemExecutionException("Expected exactly one match!");
        }
        return potentialMatches.iterator().next().sue;
    }

    private BiFunction<Sue, Sue, Boolean> partOneSueMatcher() {
        return (s1, s2) ->
                (s1.children == UNKNOWN || s2.children == UNKNOWN || s1.children == s2.children)
                        && (s1.cats == UNKNOWN || s2.cats == UNKNOWN || s1.cats == s2.cats)
                        && (s1.samoyeds == UNKNOWN || s2.samoyeds == UNKNOWN || s1.samoyeds == s2.samoyeds)
                        && (s1.pomeranians == UNKNOWN || s2.pomeranians == UNKNOWN || s1.pomeranians == s2.pomeranians)
                        && (s1.akitas == UNKNOWN || s2.akitas == UNKNOWN || s1.akitas == s2.akitas)
                        && (s1.vizslas == UNKNOWN || s2.vizslas == UNKNOWN || s1.vizslas == s2.vizslas)
                        && (s1.goldfish == UNKNOWN || s2.goldfish == UNKNOWN || s1.goldfish == s2.goldfish)
                        && (s1.trees == UNKNOWN || s2.trees == UNKNOWN || s1.trees == s2.trees)
                        && (s1.cars == UNKNOWN || s2.cars == UNKNOWN || s1.cars == s2.cars)
                        && (s1.perfumes == UNKNOWN || s2.perfumes == UNKNOWN || s1.perfumes == s2.perfumes);
    }

    private BiFunction<Sue, Sue, Boolean> partTwoMatcher() {
        return (s1, s2) ->
                (s1.children == UNKNOWN || s2.children == UNKNOWN || s1.children == s2.children)
                        && (s1.cats == UNKNOWN || s2.cats == UNKNOWN || s1.cats < s2.cats)
                        && (s1.samoyeds == UNKNOWN || s2.samoyeds == UNKNOWN || s1.samoyeds == s2.samoyeds)
                        && (s1.pomeranians == UNKNOWN || s2.pomeranians == UNKNOWN || s1.pomeranians > s2.pomeranians)
                        && (s1.akitas == UNKNOWN || s2.akitas == UNKNOWN || s1.akitas == s2.akitas)
                        && (s1.vizslas == UNKNOWN || s2.vizslas == UNKNOWN || s1.vizslas == s2.vizslas)
                        && (s1.goldfish == UNKNOWN || s2.goldfish == UNKNOWN || s1.goldfish > s2.goldfish)
                        && (s1.trees == UNKNOWN || s2.trees == UNKNOWN || s1.trees < s2.trees)
                        && (s1.cars == UNKNOWN || s2.cars == UNKNOWN || s1.cars == s2.cars)
                        && (s1.perfumes == UNKNOWN || s2.perfumes == UNKNOWN || s1.perfumes == s2.perfumes);
    }

    private List<Sue> loadIndexedIdentifiers(final ResourceLoader resourceLoader) {
        final List<String> lines = resourceLoader.resources();
        final List<Sue> sues = new ArrayList<>(lines.size());

        for (final String line : lines) {
            final Sue sue = new Sue();

            final Matcher nameMatcher = SUE_REGEX.matcher(line);
            nameMatcher.find();
            final String index = nameMatcher.group();

            sue.sue = Long.parseLong(index.replace("Sue ", "").replace(":", ""));

            final String properties = line.replace(index, "");
            final String[] split = properties.replaceAll("\\s+", "").split(",");
            for (String s : split) {
                String[] propNameAndValue = s.split(":");
                sue.set(propNameAndValue[0], Long.parseLong(propNameAndValue[1]));
            }
            sues.add(sue);
        }

        return sues;
    }

    private static final class Sue {
        private long sue = UNKNOWN;
        private long children = UNKNOWN;
        private long cats = UNKNOWN;
        private long samoyeds = UNKNOWN;
        private long pomeranians = UNKNOWN;
        private long akitas = UNKNOWN;
        private long vizslas = UNKNOWN;
        private long goldfish = UNKNOWN;
        private long trees = UNKNOWN;
        private long cars = UNKNOWN;
        private long perfumes = UNKNOWN;

        private void set(final String property, final long value) {
            switch (property) {
                case "children":
                    this.children = value;
                    return;
                case "cats":
                    this.cats = value;
                    return;
                case "samoyeds":
                    this.samoyeds = value;
                    return;
                case "pomeranians":
                    this.pomeranians = value;
                    return;
                case "akitas":
                    this.akitas = value;
                    return;
                case "vizslas":
                    this.vizslas = value;
                    return;
                case "goldfish":
                    this.goldfish = value;
                    return;
                case "trees":
                    this.trees = value;
                    return;
                case "cars":
                    this.cars = value;
                    return;
                case "perfumes":
                    this.perfumes = value;
                    return;
                case "Sue":
                    this.sue = value;
                    return;
                default:
                    throw new ProblemExecutionException("Undefined property");
            }
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sue that = (Sue) o;
            return children == that.children
                    && cats == that.cats
                    && samoyeds == that.samoyeds
                    && pomeranians == that.pomeranians
                    && akitas == that.akitas
                    && vizslas == that.vizslas
                    && goldfish == that.goldfish
                    && trees == that.trees
                    && cars == that.cars
                    && perfumes == that.perfumes;
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    children,
                    cats,
                    samoyeds,
                    pomeranians,
                    akitas,
                    vizslas,
                    goldfish,
                    trees,
                    cars,
                    perfumes
            );
        }

        @Override
        public String toString() {
            return "Sue{"
                    + "sue=" + sue
                    + ", children=" + children
                    + ", cats=" + cats
                    + ", samoyeds=" + samoyeds
                    + ", pomeranians=" + pomeranians
                    + ", akitas=" + akitas
                    + ", vizslas=" + vizslas
                    + ", goldfish=" + goldfish
                    + ", trees=" + trees
                    + ", cars=" + cars
                    + ", perfumes=" + perfumes
                    + '}';
        }
    }

}
