package com.rev.aoc.cli;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.AocEngine;
import org.apache.commons.cli.*;

public class CliParser
{
    private static final CommandLineParser PARSER = new DefaultParser();

    public static AocEngine parse(String[] args)
    {
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
            printHelp(options);
            return null;
        }
    }

    private static void printHelp(Options options)
    {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("aoc", options);
    }

    private static AocEngine parse(CommandLine cl) throws ParseException
    {
        validateOptions(cl);
        AocCoordinate firstAocCoordinate = parseAocCoordinate(cl.getOptionValue(CliOptions.PROBLEM_NUMBER));
        AocCoordinate secondAocCoordinate = parseAocCoordinate(cl.getOptionValue(CliOptions.PROBLEM_OTHER_NUMBER));

        if (firstAocCoordinate != null && secondAocCoordinate != null &&
                firstAocCoordinate.compareTo(secondAocCoordinate) < 0) {
            return new AocEngine(secondAocCoordinate, firstAocCoordinate);
        }
        return new AocEngine(firstAocCoordinate, secondAocCoordinate);
    }

    private static AocCoordinate parseAocCoordinate(String optionValue) throws ParseException
    {
        if (optionValue == null) {
            return null;
        }
        AocCoordinate parsed = AocCoordinate.parse(optionValue);
        if (parsed != null) {
            return parsed;
        }
        throw new ParseException(String.format("Cannot parse '%s' to advent of code problem number", optionValue));
    }

    private static void validateOptions(CommandLine cl) throws ParseException
    {
        if (!cl.hasOption(CliOptions.PROBLEM_NUMBER)) {
            if (cl.hasOption(CliOptions.PROBLEM_OTHER_NUMBER)) {
                throw new ParseException(String.format("The '%s' option cannot be set without the '%s' option", CliOptions.PROBLEM_OTHER_NUMBER, CliOptions.PROBLEM_NUMBER));
            }
        }
    }
}
