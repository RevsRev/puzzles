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
        return CliParser.parse(args);
    }
}
