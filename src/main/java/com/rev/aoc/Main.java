package com.rev.aoc;

import com.rev.aoc.framework.io.cli.CliParser;
import com.rev.aoc.framework.AocEngine;
import com.rev.aoc.framework.io.load.AocInputLoader;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;
import java.util.ServiceLoader;

public final class Main {

    public static final String PROPERTIES_FILE = "aoc.properties";
    @Getter
    private static AocInputLoader inputLoader;

    private Main() {
    }

    public static void main(final String[] args) {
        loadProperties(Arrays.asList(args).contains("-d"));
        loadAocProblemLoader();
        AocEngine engine = CliParser.parse(args);
        if (engine == null) {
            return;
        }
        engine.run();
    }

    private static void loadAocProblemLoader() {
        ServiceLoader<AocInputLoader> inputLoaders = ServiceLoader.load(AocInputLoader.class);
        long count = inputLoaders.stream().count();
        if (count > 1) {
            System.out.println("WARNING: Multiple AocInputLoader instances found.");
        }
        Optional<AocInputLoader> inputLoaderOptional = inputLoaders.findFirst();
        if (inputLoaderOptional.isEmpty()) {
            throw new RuntimeException("No AocInputLoader found");
        }

        inputLoader = inputLoaderOptional.get();
        if (count > 1) {
            System.out.println("WARNING: Loaded " + inputLoader.getClass().getName());
        }

    }

    private static void loadProperties(final boolean debug) {
        InputStream is = null;
        boolean logged = false;
        try {
            is = Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            if (is == null) {
                throw new FileNotFoundException(PROPERTIES_FILE);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            System.getProperties().load(reader);
        } catch (Exception e) {
            logPropertiesLoadError();
            logStacktrace(debug, e);
            logged = true;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    if (!logged) {
                        logPropertiesLoadError();
                    }
                }
            }
        }
    }

    private static void logStacktrace(final boolean debug, final Throwable error) {
        if (debug) {
            error.printStackTrace(System.out);
        }
    }

    private static void logPropertiesLoadError() {
        System.out.printf("Properties could not be loaded from '%s'. Visualisations may not work%n", PROPERTIES_FILE);
    }
}
