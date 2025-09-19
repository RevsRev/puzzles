package com.rev.puzzles.framework.framework;

import java.util.Set;

public interface EngineLoader {
    Set<String> getEngines();
    ProblemEngine<?> loadEngine(String engineName, String[] args);
}
