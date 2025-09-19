package com.rev.puzzles.app.io;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public final class CliOptions {
    public static final Option HELP = helpOption();
    public static final Option LIST_ENGINES = listEnginesOption();
    public static final Option ENGINE = engineOption();

    private CliOptions() {
    }

    public static Options getOptions() {
        Options options = new Options();
        options.addOption(HELP);
        options.addOption(LIST_ENGINES);
        options.addOption(ENGINE);
        return options;
    }

    private static Option helpOption() {
        return Option.builder()
                .option("h")
                .longOpt("help")
                .desc("Print this message")
                .build();
    }

    private static Option engineOption() {
        return Option.builder()
                .option("e")
                .longOpt("engine")
                .desc("The problem execution engine to use.")
                .hasArg()
                .type(String.class)
                .build();
    }

    private static Option listEnginesOption() {
        return Option.builder()
                .option("le")
                .longOpt("list-engines")
                .desc("List available problem execution engines.")
                .build();
    }
}
