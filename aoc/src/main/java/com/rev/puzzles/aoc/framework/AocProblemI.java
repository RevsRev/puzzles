package com.rev.puzzles.aoc.framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AocProblemI {
    int year();

    int day();

    int part();
}
