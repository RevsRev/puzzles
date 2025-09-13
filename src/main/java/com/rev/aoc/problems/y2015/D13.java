package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;
import com.rev.aoc.util.graph.Edge;
import com.rev.aoc.util.graph.Graph;
import com.rev.aoc.util.graph.Vertex;
import com.rev.aoc.util.graph.algo.TravellingSalesman;

import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class D13 extends AocProblem<Long, Long> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern FIRST_WORD = Pattern.compile("^\\w*");
    private static final Pattern LAST_WORD_WITH_PERIOD = Pattern.compile("\\w*\\.$");

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 13);
    }

    @AocProblemI(year = 2015, day = 13, part = 1)
    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected Long partOneImpl() {
        Graph<Vertex, Edge> directedHappinessGraph = Graph.fromResources(
                        loadResources(),
                        getLineProcessor(),
                        true)
                .build();

        Graph<Vertex, Edge> aggregatedHappinessGraph = Graph.aggregate(
                directedHappinessGraph,
                (e1, e2) -> {
                    if (e1.isEmpty() || e2.isEmpty()) {
                        throw new ProblemExecutionException("Badly defined problem");
                    }
                    return e1.get().getWeight() + e2.get().getWeight();
                });

        return -1 * TravellingSalesman.heldKarp(aggregatedHappinessGraph);
    }

    @AocProblemI(year = 2015, day = 13, part = 1)
    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected Long partTwoImpl() {
        Graph.Builder<Vertex, Edge> directedHappinessGraphBuilder = Graph.fromResources(
                loadResources(),
                getLineProcessor(),
                true);

        final String me = "ME";
        directedHappinessGraphBuilder.addVertex(me);

        directedHappinessGraphBuilder.getVertices().forEach(
                v -> {
                    directedHappinessGraphBuilder.addEdge(me, v.getName(), 0);
                    directedHappinessGraphBuilder.addEdge(v.getName(), me, 0);
                }
        );

        Graph<Vertex, Edge> aggregatedHappinessGraph = Graph.aggregate(
                directedHappinessGraphBuilder.build(),
                (e1, e2) -> {
                    if (e1.isEmpty() || e2.isEmpty()) {
                        throw new ProblemExecutionException("Badly defined problem");
                    }
                    return e1.get().getWeight() + e2.get().getWeight();
                });

        return -1 * TravellingSalesman.heldKarp(aggregatedHappinessGraph);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private BiConsumer<String, Graph.Builder<Vertex, Edge>> getLineProcessor() {
        return (line, builder) -> {
            final long weight = extractWeight(line);
            final long sign = line.contains("gain") ? -1 : 1;
            final String firstName = extractFirstName(line);
            final String secondName = extractSecondName(line);
            builder.addVertex(firstName);
            builder.addVertex(secondName);
            builder.addEdge(firstName, secondName, sign * weight);
        };
    }

    private String extractSecondName(final String line) {
        final Matcher m = LAST_WORD_WITH_PERIOD.matcher(line);
        m.find();
        final String secondName = m.group().replaceAll("\\.", "");
        return secondName;
    }

    private String extractFirstName(final String line) {
        final Matcher m = FIRST_WORD.matcher(line);
        m.find();
        final String firstName = m.group();
        return firstName;
    }

    private long extractWeight(final String line) {
        final Matcher numberMatcher = NUMBER_PATTERN.matcher(line);
        numberMatcher.find();
        final long weight = Long.parseLong(numberMatcher.group());
        return weight;
    }

}
