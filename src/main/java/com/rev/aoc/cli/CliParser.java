package com.rev.aoc.cli;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.AocEngine;
import com.rev.aoc.AocPart;
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

    public static AocEngine parse(final String[] args) {
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

    private static AocEngine parse(final CommandLine cl) throws ParseException {
        validateOptions(cl);
        AocCoordinate firstAocCoordinate = parseAocCoordinate(
                cl.getOptionValue(CliOptions.PROBLEM_NUMBER));
        AocCoordinate secondAocCoordinate = parseAocCoordinate(
                cl.getOptionValue(CliOptions.PROBLEM_OTHER_NUMBER));

        AocPart part = AocPart.ALL;
        if (cl.hasOption(CliOptions.PROBLEM_PART)) {
            part = AocPart.valueOf(cl.getParsedOptionValue(CliOptions.PROBLEM_PART));
        }

        AocEngine engine;
        if (firstAocCoordinate != null && secondAocCoordinate != null
                && firstAocCoordinate.compareTo(secondAocCoordinate) < 0) {
            engine = new AocEngine(secondAocCoordinate, firstAocCoordinate, part);
        } else {
            engine = new AocEngine(firstAocCoordinate, secondAocCoordinate, part);
        }
        engine.setDebug(cl.hasOption(CliOptions.DEBUG));
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
