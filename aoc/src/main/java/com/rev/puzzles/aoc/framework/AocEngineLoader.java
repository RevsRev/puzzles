package com.rev.puzzles.aoc.framework;

import com.rev.puzzles.aoc.framework.parse.CliParser;
import com.rev.puzzles.framework.framework.EngineLoader;
import com.rev.puzzles.framework.framework.ProblemEngine;

import java.util.Set;

public final class AocEngineLoader implements EngineLoader {

    private static final String AOC = "aoc";
    private static final String AOC_VISUALISE = "aoc-vis";

    @Override
    public Set<String> getEngines() {
        return Set.of(AOC, AOC_VISUALISE);
    }

    @Override
    public ProblemEngine<?> loadEngine(final String engineName, final String[] args) {
        if (!(AOC.equals(engineName) || AOC_VISUALISE.equals(engineName))) {
            throw new RuntimeException(String.format("Unknown engine '%s'", engineName));
        }

        boolean visualise = AOC_VISUALISE.equals(engineName);
        return CliParser.parse(args, visualise);
    }
}
