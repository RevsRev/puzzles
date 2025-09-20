package com.rev.puzzles.leet.framework;

import com.rev.puzzles.framework.framework.EngineLoader;
import com.rev.puzzles.framework.framework.ProblemEngine;
import com.rev.puzzles.leet.framework.parse.CliParser;

import java.util.Set;

public final class LeetEngineLoader implements EngineLoader {

    private static final String LEET = "leet";
    private static final String LEET_GEN = "leet-gen";

    @Override
    public Set<String> getEngines() {
        return Set.of(LEET, LEET_GEN);
    }

    @Override
    public ProblemEngine<?> loadEngine(final String engineName, final String[] args) {
        if (!(LEET.equals(engineName) || LEET_GEN.equals(engineName))) {
            throw new RuntimeException(String.format("Unrecognised engine '%s", engineName));
        }
        final boolean gen = LEET_GEN.equals(engineName);
        return CliParser.parse(args, gen);
    }
}
