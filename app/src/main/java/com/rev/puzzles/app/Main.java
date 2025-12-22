package com.rev.puzzles.app;

import com.rev.puzzles.framework.framework.ProblemEngine;
import com.rev.puzzles.app.io.CliParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public final class Main {

    public static final String PROPERTIES_FILE = "aoc.properties";

    private Main() {
    }

    public static void main(final String[] args) {
        loadProperties(Arrays.asList(args).contains("-d"));
        ProblemEngine engine = CliParser.parse(args);
        if (engine == null) {
            return;
        }
        try {
            engine.run();
        } finally {
            engine.shutdown();
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
