package com.rev.aoc;

import com.rev.aoc.cli.CliParser;

public class Main
{
    public static void main(String[] args)
    {
        AocEngine engine = CliParser.parse(args);
        if (engine == null) {
            return;
        }
        engine.run();
    }
}