package com.rev.puzzles.app.io;

import com.rev.puzzles.framework.framework.impl.EngineFactory;
import com.rev.puzzles.framework.framework.ProblemEngine;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public final class CliParser {
    private static final CommandLineParser PARSER = new DefaultParser();

    private CliParser() {
    }

    public static ProblemEngine parse(final String[] args) {
        Options options = CliOptions.getOptions();
        try {
            CommandLine cl = PARSER.parse(options, args, false);

            if (cl.hasOption(CliOptions.ENGINE)) {
                final String engine = cl.getOptionValue(CliOptions.ENGINE);
                return getEngine(engine, stripEngineArg(engine, args));
            }

            if (cl.hasOption(CliOptions.LIST_ENGINES)) {
                listEngines();
                return null;
            }

            printHelp(options);
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            Get the actual arg rather than using cli, in case it was the cli that failed!
            if (Arrays.stream(args).anyMatch(s -> "-d".equals(s) || "--debug".equals(s))) {
                PrintWriter pw = new PrintWriter(System.out);
                e.printStackTrace(pw);
                pw.flush();
            }
            printHelp(options);
            return null;
        }
    }

    private static String[] stripEngineArg(final String engineName, final String[] args) {
        return Arrays
                .stream(args)
                .filter(s -> !("--engine".equals(s) || engineName.equals(s)))
                .toList()
                .toArray(new String[0]);
    }

    private static ProblemEngine getEngine(final String engine, final String[] args) {
        return EngineFactory.factory(engine, args);
    }

    private static void listEngines() {
        System.out.println("Available Engines:\n\n");
        final List<String> engines = EngineFactory.listEngines();
        //TODO - Replace with a print writer!
        engines.forEach(engine -> System.out.println(" - " + engine));
    }

    private static void printHelp(final Options options) {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("aoc", options);
    }
}
