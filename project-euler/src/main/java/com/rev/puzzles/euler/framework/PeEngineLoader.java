package com.rev.puzzles.euler.framework;

import com.rev.puzzles.euler.framework.parse.CliParser;
import com.rev.puzzles.framework.framework.EngineLoader;
import com.rev.puzzles.framework.framework.ProblemEngine;

import java.util.Set;

public final class PeEngineLoader implements EngineLoader {

    private static final String PROJECT_EULER = "project-euler";

    @Override
    public Set<String> getEngines() {
        return Set.of(PROJECT_EULER);
    }

    @Override
    public ProblemEngine<?> loadEngine(final String engineName, final String[] args) {
        if (!PROJECT_EULER.equals(engineName)) {
            throw new RuntimeException(String.format("Unrecognised engine '%s", engineName));
        }
        return CliParser.parse(args);
    }
}
