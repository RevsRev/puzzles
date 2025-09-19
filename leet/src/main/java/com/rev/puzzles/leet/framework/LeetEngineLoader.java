package com.rev.puzzles.leet.framework;

import com.rev.puzzles.framework.framework.EngineLoader;
import com.rev.puzzles.framework.framework.ProblemEngine;
import com.rev.puzzles.leet.framework.parse.CliParser;

import java.util.Set;

public final class LeetEngineLoader implements EngineLoader {

    private static final String LEET = "leet";

    @Override
    public Set<String> getEngines() {
        return Set.of(LEET);
    }

    @Override
    public ProblemEngine<?> loadEngine(final String engineName, final String[] args) {
        return CliParser.parse(args);
    }
}
