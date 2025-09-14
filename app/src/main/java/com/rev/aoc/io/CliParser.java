package com.rev.aoc.io;

import com.rev.aoc.framework.AnnotationProblemLoader;
import com.rev.aoc.framework.AocCoordinate;
import com.rev.aoc.framework.AocExecutor;
import com.rev.aoc.framework.AocProblemI;
import com.rev.aoc.framework.AocVisualisation;
import com.rev.aoc.framework.ExecutorListener;
import com.rev.aoc.AocExecutionListenerPrinter;
import com.rev.aoc.framework.NoOpExecutorListener;
import com.rev.aoc.framework.ProblemEngine;
import com.rev.aoc.framework.ProblemExecutor;
import com.rev.aoc.framework.ProblemLoader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.Arrays;

import static com.rev.aoc.io.CliOptions.DEBUG;
import static com.rev.aoc.io.CliOptions.PROBLEM_NUMBER;
import static com.rev.aoc.io.CliOptions.PROBLEM_OTHER_NUMBER;
import static com.rev.aoc.io.CliOptions.PROBLEM_VISUALISE;

public final class CliParser {
    private static final CommandLineParser PARSER = new DefaultParser();

    private CliParser() {
    }

    public static ProblemEngine parse(final String[] args) {
        Options options = CliOptions.getOptions();
        try {
            CommandLine cl = PARSER.parse(options, args);

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
        AocCoordinate firstAocCoordinate = parseAocCoordinate(
                cl.getOptionValue(PROBLEM_NUMBER));
        AocCoordinate secondAocCoordinate = parseAocCoordinate(
                cl.getOptionValue(PROBLEM_OTHER_NUMBER));

        final boolean visualise = cl.hasOption(PROBLEM_VISUALISE);
        final AnnotationProblemLoader<?, AocCoordinate> problemLoader = getProblemLoader(visualise);
        final ExecutorListener<AocCoordinate> executorListener = visualise
                ? new NoOpExecutorListener<>()
                : new AocExecutionListenerPrinter();

        final ProblemEngine<AocCoordinate> engine = getAocCoordinateProblemEngine(
                firstAocCoordinate,
                secondAocCoordinate,
                problemLoader,
                executorListener);

        engine.setDebug(cl.hasOption(DEBUG));
        return engine;
    }

    private static AnnotationProblemLoader<?, AocCoordinate> getProblemLoader(boolean visualise) {
        if (visualise) {
            return new AnnotationProblemLoader<>(
                    AocVisualisation.class,
                    visualisation -> new AocCoordinate(
                            visualisation.year(),
                            visualisation.day(),
                            visualisation.part()
                    )
            );
        }
        return new AnnotationProblemLoader<>(
                AocProblemI.class,
                problemI -> new AocCoordinate(
                        problemI.year(),
                        problemI.day(),
                        problemI.part()
                )
        );
    }

    private static ProblemEngine<AocCoordinate> getAocCoordinateProblemEngine(
            final AocCoordinate firstAocCoordinate,
            final AocCoordinate secondAocCoordinate,
            final ProblemLoader<AocCoordinate> problemLoader,
            final ExecutorListener<AocCoordinate> executorListener) {

        final ProblemExecutor<AocCoordinate> problemExecutor = new AocExecutor(executorListener);

        if (firstAocCoordinate != null && secondAocCoordinate != null) {
            if (firstAocCoordinate.compareTo(secondAocCoordinate) < 0) {
                return new ProblemEngine<>(problemLoader, problemExecutor, firstAocCoordinate, secondAocCoordinate);
            } else {
                return new ProblemEngine<>(problemLoader, problemExecutor, secondAocCoordinate, firstAocCoordinate);
            }
        }

        return new ProblemEngine<>(problemLoader, problemExecutor, firstAocCoordinate, secondAocCoordinate);
    }

    private static AocCoordinate parseAocCoordinate(final String optionValue)
            throws ParseException {
        if (optionValue == null) {
            return null;
        }
        AocCoordinate parsed = AocCoordinate.parse(optionValue);
        if (parsed != null) {
            return parsed;
        }
        throw new ParseException(String.format(
                "Cannot parse '%s' to advent of code problem number",
                optionValue));
    }

    private static void validateOptions(final CommandLine cl) throws ParseException {
        if (!cl.hasOption(PROBLEM_NUMBER)) {
            if (cl.hasOption(PROBLEM_OTHER_NUMBER)) {
                throw new ParseException(String.format(
                        "The '%s' option cannot be set without the '%s' option",
                        PROBLEM_OTHER_NUMBER,
                        PROBLEM_NUMBER));
            }
        }
    }
}
