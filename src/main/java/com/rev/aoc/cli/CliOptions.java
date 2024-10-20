package com.rev.aoc.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public final class CliOptions {
    public static final Option HELP = helpOption();
    public static final Option PROBLEM_NUMBER = problemNumberOption();
    public static final Option PROBLEM_OTHER_NUMBER =
            problemOtherNumberOption();

    private CliOptions() {
    }

    public static Options getOptions() {
        Options options = new Options();
        options.addOption(HELP);
        options.addOption(PROBLEM_NUMBER);
        options.addOption(PROBLEM_OTHER_NUMBER);
        return options;
    }

    private static Option helpOption() {
        return Option.builder()
                .option("h")
                .longOpt("help")
                .desc("Print this message")
                .build();
    }

    private static Option problemNumberOption() {
        return Option.builder()
                .option("p")
                .longOpt("problem")
                .desc("The problem to be executed in yy:dd or yyyy:dd format. "
                       +  "If omitted, all problems will be solved")
                .hasArg()
                .type(String.class)
                .build();
    }

    private static Option problemOtherNumberOption() {
        return Option.builder()
                .option("pb")
                .longOpt("problem-bound")
                .desc("A second problem to be executed in yy:dd or yyyy:dd format. "
                        + "All problems between -p and -pb will be executed (inclusive)")
                .hasArg()
                .type(String.class)
                .build();
    }

}
