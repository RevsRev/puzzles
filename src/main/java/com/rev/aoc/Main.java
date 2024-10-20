package com.rev.aoc;

import com.rev.aoc.cli.CliParser;

public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        AocEngine engine = CliParser.parse(args);
        if (engine == null) {
            return;
        }
        engine.run();
    }
}
