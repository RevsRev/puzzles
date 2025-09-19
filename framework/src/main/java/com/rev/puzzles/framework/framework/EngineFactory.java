package com.rev.puzzles.framework.framework;

import java.util.List;
import java.util.ServiceLoader;

public final class EngineFactory {

    private EngineFactory() {
    }


    public static ProblemEngine<?> factory(final String engineName, final String[] args) {

        ServiceLoader<EngineLoader> loaders = ServiceLoader.load(EngineLoader.class);
        List<ServiceLoader.Provider<EngineLoader>> matchingLoaders = loaders
                .stream()
                .filter(loader -> loader.get().getEngines().contains(engineName)).toList();

        if (matchingLoaders.isEmpty()) {
            throw new RuntimeException("[\u001B[31mFatal\u001B[0m] No engine matching '" + engineName + "' exists");
        }

        if (matchingLoaders.size() > 1) {
            throw new RuntimeException(
                    "[\u001B[31mFatal\u001B[0m] Multiple engines matching '" + engineName + "' exists");
        }

        return matchingLoaders.get(0).get().loadEngine(engineName, args);
    }

    public static List<String> listEngines() {
        final ServiceLoader<EngineLoader> loaders = ServiceLoader.load(EngineLoader.class);
        return loaders.stream().flatMap(loader -> loader.get().getEngines().stream()).toList();
    }

//    public static ResourceLoader loadResources(final AocCoordinate coordinate) {
//        return () -> {
//            try {
//                return factory().load(coordinate);
//            } catch (IOException e) {
//                String msg = String.format("Could not load resource for problem %s", coordinate);
//                throw new ProblemExecutionException(msg, e);
//            }
//        };
//    }
}
