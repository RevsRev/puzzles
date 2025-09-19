package com.rev.puzzles.aoc.framework.parse;

import com.rev.puzzles.aoc.framework.AocCoordinate;
import com.rev.puzzles.aoc.framework.AocExecutionListenerPrinter;
import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.aoc.framework.AocResourceLoader;
import com.rev.puzzles.aoc.framework.AocVisualisation;
import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.ProblemEngine;
import com.rev.puzzles.framework.framework.ProblemExecutor;
import com.rev.puzzles.framework.framework.ProblemLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.impl.AnnotationProblemLoader;
import com.rev.puzzles.framework.framework.impl.DefaultExecutor;
import com.rev.puzzles.framework.framework.impl.DefaultProblemEngine;
import com.rev.puzzles.framework.framework.impl.NoOpExecutorListener;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.Arrays;

import static com.rev.puzzles.aoc.framework.parse.CliOptions.DEBUG;
import static com.rev.puzzles.aoc.framework.parse.CliOptions.PROBLEM_NUMBER;
import static com.rev.puzzles.aoc.framework.parse.CliOptions.PROBLEM_OTHER_NUMBER;
import static com.rev.puzzles.aoc.framework.parse.CliOptions.PROBLEM_VISUALISE;


public final class CliParser {
    private static final CommandLineParser PARSER = new DefaultParser();
    private static final String AOC_PROBLEMS_PACKAGE = "com.rev.puzzles.aoc.problems";

    private CliParser() {
    }

    public static ProblemEngine parse(final String[] args) {
        Options options = CliOptions.getOptions();
        try {
            CommandLine cl = PARSER.parse(options, args, false);

            if (cl.hasOption(CliOptions.HELP)) {
                printHelp(options);
                return null;
            }
            return parse(cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            Get the actual arg rather than using cli, incase it was the cli that failed!
            if (Arrays.stream(args).anyMatch(s -> "-d".equals(s) || "--debug".equals(s))) {
                PrintWriter pw = new PrintWriter(System.out);
                e.printStackTrace(pw);
                pw.flush();
            }
            printHelp(options);
            return null;
        }
    }

    private static void printHelp(final Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("aoc", options);
    }

    private static ProblemEngine parse(final CommandLine cl) throws ParseException {
        validateOptions(cl);
        AocCoordinate firstAocCoordinate = parseAocCoordinate(cl.getOptionValue(PROBLEM_NUMBER));
        AocCoordinate secondAocCoordinate = parseAocCoordinate(cl.getOptionValue(PROBLEM_OTHER_NUMBER));

        final boolean visualise = cl.hasOption(PROBLEM_VISUALISE);
        final AnnotationProblemLoader<?, AocCoordinate> problemLoader = getProblemLoader(visualise);
        final ExecutorListener<AocCoordinate> executorListener =
                visualise ? new NoOpExecutorListener<>() : new AocExecutionListenerPrinter();

        final AocResourceLoader resourceLoader = new AocResourceLoader();

        final DefaultProblemEngine<AocCoordinate> engine =
                getAocCoordinateProblemEngine(firstAocCoordinate, secondAocCoordinate, problemLoader, executorListener,
                        resourceLoader);

        engine.setDebug(cl.hasOption(DEBUG));
        return engine;
    }

    private static AnnotationProblemLoader<?, AocCoordinate> getProblemLoader(boolean visualise) {
        if (visualise) {
            return new AnnotationProblemLoader<>(AOC_PROBLEMS_PACKAGE, AocVisualisation.class,
                    visualisation -> new AocCoordinate(visualisation.year(), visualisation.day(),
                            visualisation.part()));
        }
        return new AnnotationProblemLoader<>(AOC_PROBLEMS_PACKAGE, AocProblemI.class,
                problemI -> new AocCoordinate(problemI.year(), problemI.day(), problemI.part()));
    }

    private static DefaultProblemEngine<AocCoordinate> getAocCoordinateProblemEngine(
            final AocCoordinate firstAocCoordinate, final AocCoordinate secondAocCoordinate,
            final ProblemLoader<AocCoordinate> problemLoader, final ExecutorListener<AocCoordinate> executorListener,
            final ResourceLoader<AocCoordinate> resourceLoader) {

        final ProblemExecutor<AocCoordinate> problemExecutor = new DefaultExecutor<>(executorListener, resourceLoader);

        if (firstAocCoordinate != null && secondAocCoordinate != null) {
            if (firstAocCoordinate.compareTo(secondAocCoordinate) < 0) {
                return new DefaultProblemEngine<>(problemLoader, problemExecutor, firstAocCoordinate,
                        secondAocCoordinate);
            } else {
                return new DefaultProblemEngine<>(problemLoader, problemExecutor, secondAocCoordinate,
                        firstAocCoordinate);
            }
        }

        return new DefaultProblemEngine<>(problemLoader, problemExecutor, firstAocCoordinate, secondAocCoordinate);
    }

    private static AocCoordinate parseAocCoordinate(final String optionValue) throws ParseException {
        if (optionValue == null) {
            return null;
        }
        AocCoordinate parsed = AocCoordinate.parse(optionValue);
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
