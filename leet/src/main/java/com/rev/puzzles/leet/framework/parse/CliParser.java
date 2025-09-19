package com.rev.puzzles.leet.framework.parse;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.ProblemEngine;
import com.rev.puzzles.framework.framework.ProblemExecutor;
import com.rev.puzzles.framework.framework.ProblemLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.impl.AnnotationProblemLoader;
import com.rev.puzzles.framework.framework.impl.DefaultExecutor;
import com.rev.puzzles.framework.framework.impl.DefaultProblemEngine;
import com.rev.puzzles.framework.framework.impl.NoOpExecutorListener;
import com.rev.puzzles.framework.framework.impl.NoOpResourceLoader;
import com.rev.puzzles.leet.framework.LeetCoordinate;
import com.rev.puzzles.leet.framework.LeetExecutionListenerPrinter;
import com.rev.puzzles.leet.framework.LeetProblem;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.Arrays;

import static com.rev.puzzles.leet.framework.parse.CliOptions.DEBUG;
import static com.rev.puzzles.leet.framework.parse.CliOptions.PROBLEM_NUMBER;
import static com.rev.puzzles.leet.framework.parse.CliOptions.PROBLEM_OTHER_NUMBER;
import static com.rev.puzzles.leet.framework.parse.CliOptions.PROBLEM_VISUALISE;


public final class CliParser {
    private static final CommandLineParser PARSER = new DefaultParser();
    private static final String LEET_PROBLEMS_PACKAGE = "com.rev.puzzles.leet.problems";

    private CliParser() {
    }

    public static ProblemEngine parse(final String[] args) {
        final Options options = CliOptions.getOptions();
        try {
            final CommandLine cl = PARSER.parse(options, args, false);

            if (cl.hasOption(CliOptions.HELP)) {
                printHelp(options);
                return null;
            }
            return parse(cl);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
//            Get the actual arg rather than using cli, incase it was the cli that failed!
            if (Arrays.stream(args).anyMatch(s -> "-d".equals(s) || "--debug".equals(s))) {
                final PrintWriter pw = new PrintWriter(System.out);
                e.printStackTrace(pw);
                pw.flush();
            }
            printHelp(options);
            return null;
        }
    }

    private static void printHelp(final Options options) {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("aoc", options);
    }

    private static ProblemEngine parse(final CommandLine cl) throws ParseException {
        validateOptions(cl);
        final LeetCoordinate firstLeetCoordinate = parseLeetCoordinate(cl.getOptionValue(PROBLEM_NUMBER));
        final LeetCoordinate secondLeetCoordinate = parseLeetCoordinate(cl.getOptionValue(PROBLEM_OTHER_NUMBER));

        final boolean visualise = cl.hasOption(PROBLEM_VISUALISE);
        final AnnotationProblemLoader<?, LeetCoordinate> problemLoader = getProblemLoader();
        final ExecutorListener<LeetCoordinate> executorListener =
                visualise ? new NoOpExecutorListener<>() : new LeetExecutionListenerPrinter();

        final ResourceLoader<LeetCoordinate> resourceLoader = new NoOpResourceLoader<>();
        final DefaultProblemEngine<LeetCoordinate> engine =
                getLeetCoordinateProblemEngine(firstLeetCoordinate, secondLeetCoordinate, problemLoader,
                        executorListener, resourceLoader);

        engine.setDebug(cl.hasOption(DEBUG));
        return engine;
    }

    private static AnnotationProblemLoader<?, LeetCoordinate> getProblemLoader() {
        return new AnnotationProblemLoader<>(LEET_PROBLEMS_PACKAGE, LeetProblem.class,
                problemI -> new LeetCoordinate(problemI.number()));
    }

    private static DefaultProblemEngine<LeetCoordinate> getLeetCoordinateProblemEngine(
            final LeetCoordinate firstLeetCoordinate, final LeetCoordinate secondLeetCoordinate,
            final ProblemLoader<LeetCoordinate> problemLoader, final ExecutorListener<LeetCoordinate> executorListener,
            final ResourceLoader<LeetCoordinate> resourceLoader) {

        final ProblemExecutor<LeetCoordinate> problemExecutor = new DefaultExecutor<>(executorListener, resourceLoader);

        if (firstLeetCoordinate != null && secondLeetCoordinate != null) {
            if (firstLeetCoordinate.compareTo(secondLeetCoordinate) < 0) {
                return new DefaultProblemEngine<>(problemLoader, problemExecutor, firstLeetCoordinate,
                        secondLeetCoordinate);
            } else {
                return new DefaultProblemEngine<>(problemLoader, problemExecutor, secondLeetCoordinate,
                        firstLeetCoordinate);
            }
        }

        return new DefaultProblemEngine<>(problemLoader, problemExecutor, firstLeetCoordinate, secondLeetCoordinate);
    }

    private static LeetCoordinate parseLeetCoordinate(final String optionValue) throws ParseException {
        if (optionValue == null) {
            return null;
        }
        final LeetCoordinate parsed = LeetCoordinate.parse(optionValue);
        if (parsed != null) {
            return parsed;
        }
        throw new ParseException(String.format("Cannot parse '%s' to advent of code problem number", optionValue));
    }

    private static void validateOptions(final CommandLine cl) throws ParseException {
        if (!cl.hasOption(PROBLEM_NUMBER)) {
            if (cl.hasOption(PROBLEM_OTHER_NUMBER)) {
                throw new ParseException(
                        String.format("The '%s' option cannot be set without the '%s' option", PROBLEM_OTHER_NUMBER,
                                PROBLEM_NUMBER));
            }
        }
    }
}
