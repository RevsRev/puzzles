package com.rev.aoc.framework.io.cli;

import com.rev.aoc.framework.ExecutorListenerPrinter;
import com.rev.aoc.framework.ProblemEngine;
import com.rev.aoc.framework.ProblemExecutor;
import com.rev.aoc.framework.ProblemLoader;
import com.rev.aoc.framework.aoc.AocCoordinate;
import com.rev.aoc.framework.aoc.AocExecutor;
import com.rev.aoc.framework.aoc.AocProblemI;
import com.rev.aoc.framework.aoc.AnnotationProblemLoader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.Arrays;

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
                cl.getOptionValue(CliOptions.PROBLEM_NUMBER));
        AocCoordinate secondAocCoordinate = parseAocCoordinate(
                cl.getOptionValue(CliOptions.PROBLEM_OTHER_NUMBER));

        final ProblemEngine<AocCoordinate> engine = getAocCoordinateProblemEngine(
                firstAocCoordinate,
                secondAocCoordinate);

        engine.setDebug(cl.hasOption(CliOptions.DEBUG));
        engine.setVisualise(cl.hasOption(CliOptions.PROBLEM_VISUALISE));
        return engine;
    }

    private static ProblemEngine<AocCoordinate> getAocCoordinateProblemEngine(
            final AocCoordinate firstAocCoordinate,
            final AocCoordinate secondAocCoordinate) {

        final ProblemLoader<AocCoordinate> problemLoader = new AnnotationProblemLoader<>(
                AocProblemI.class,
                problemI -> new AocCoordinate(
                        problemI.year(),
                        problemI.day(),
                        problemI.part()
                )
        );
        final ProblemExecutor<AocCoordinate> problemExecutor = new AocExecutor(new ExecutorListenerPrinter());
        final ProblemEngine<AocCoordinate> engine;

        if (firstAocCoordinate != null && secondAocCoordinate != null) {
            if (firstAocCoordinate.compareTo(secondAocCoordinate) < 0) {
                engine = new ProblemEngine<>(problemLoader, problemExecutor, firstAocCoordinate, secondAocCoordinate);
            } else {
                engine = new ProblemEngine<>(problemLoader, problemExecutor, secondAocCoordinate, firstAocCoordinate);
            }
        } else {
            engine = new ProblemEngine<>(problemLoader, problemExecutor, firstAocCoordinate, secondAocCoordinate);
        }
        return engine;
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
        if (!cl.hasOption(CliOptions.PROBLEM_NUMBER)) {
            if (cl.hasOption(CliOptions.PROBLEM_OTHER_NUMBER)) {
                throw new ParseException(String.format(
                        "The '%s' option cannot be set without the '%s' option",
                        CliOptions.PROBLEM_OTHER_NUMBER,
                        CliOptions.PROBLEM_NUMBER));
            }
        }
    }
}
