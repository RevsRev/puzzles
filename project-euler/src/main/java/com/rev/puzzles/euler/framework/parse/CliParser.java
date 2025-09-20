package com.rev.puzzles.euler.framework.parse;

import com.rev.puzzles.euler.framework.PeCoordinate;
import com.rev.puzzles.euler.framework.PeExecutionListenerPrinter;
import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeResourceLoader;
import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.ProblemEngine;
import com.rev.puzzles.framework.framework.ProblemExecutor;
import com.rev.puzzles.framework.framework.ProblemLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.impl.AnnotationProblemLoader;
import com.rev.puzzles.framework.framework.impl.DefaultExecutor;
import com.rev.puzzles.framework.framework.impl.DefaultProblemEngine;
import com.rev.puzzles.framework.framework.io.SingleFileLoader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

import static com.rev.puzzles.euler.framework.parse.CliOptions.PROBLEM_NUMBER;
import static com.rev.puzzles.euler.framework.parse.CliOptions.PROBLEM_OTHER_NUMBER;
import static com.rev.puzzles.euler.framework.parse.CliOptions.DEBUG;

public final class CliParser {
    private static final CommandLineParser PARSER = new DefaultParser();
    private static final String LEET_PROBLEMS_PACKAGE = "com.rev.puzzles.euler";

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
        final PeCoordinate firstPeCoordinate = parsePeCoordinate(cl.getOptionValue(PROBLEM_NUMBER));
        final PeCoordinate secondPeCoordinate = parsePeCoordinate(cl.getOptionValue(PROBLEM_OTHER_NUMBER));

        final String leetProblemPath = Optional.ofNullable(System.getProperty("project-euler.gen-path"))
                .orElse(System.getProperty("user.dir") + "/problems/project-euler");

        final AnnotationProblemLoader<?, PeCoordinate> problemLoader = getProblemLoader();
        final ExecutorListener<PeCoordinate> executorListener = new PeExecutionListenerPrinter();

        final ResourceLoader<PeCoordinate> resourceLoader = getResourceLoader(leetProblemPath);
        final DefaultProblemEngine<PeCoordinate> engine =
                getPeCoordinateProblemEngine(firstPeCoordinate, secondPeCoordinate, problemLoader,
                        executorListener, resourceLoader);

        engine.setDebug(cl.hasOption(DEBUG));
        return engine;
    }

    private static ResourceLoader<PeCoordinate> getResourceLoader(final String leetProblemPath) {
        final SingleFileLoader<PeCoordinate> singleFileLoader =
                new SingleFileLoader<>(leetProblemPath, l -> "/L" + l.toString() + ".txt");
        return new PeResourceLoader(singleFileLoader);
    }

    private static AnnotationProblemLoader<?, PeCoordinate> getProblemLoader() {
        return new AnnotationProblemLoader<>(LEET_PROBLEMS_PACKAGE, PeProblem.class,
                problemI -> new PeCoordinate(problemI.number()));
    }

    private static DefaultProblemEngine<PeCoordinate> getPeCoordinateProblemEngine(
            final PeCoordinate firstPeCoordinate, final PeCoordinate secondPeCoordinate,
            final ProblemLoader<PeCoordinate> problemLoader, final ExecutorListener<PeCoordinate> executorListener,
            final ResourceLoader<PeCoordinate> resourceLoader) {

        final ProblemExecutor<PeCoordinate> problemExecutor = new DefaultExecutor<>(executorListener, resourceLoader);

        if (firstPeCoordinate != null && secondPeCoordinate != null) {
            if (firstPeCoordinate.compareTo(secondPeCoordinate) < 0) {
                return new DefaultProblemEngine<>(problemLoader, problemExecutor, firstPeCoordinate,
                        secondPeCoordinate);
            } else {
                return new DefaultProblemEngine<>(problemLoader, problemExecutor, secondPeCoordinate,
                        firstPeCoordinate);
            }
        }

        return new DefaultProblemEngine<>(problemLoader, problemExecutor, firstPeCoordinate, secondPeCoordinate);
    }

    private static PeCoordinate parsePeCoordinate(final String optionValue) throws ParseException {
        if (optionValue == null) {
            return null;
        }
        final PeCoordinate parsed = PeCoordinate.parse(optionValue);
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
