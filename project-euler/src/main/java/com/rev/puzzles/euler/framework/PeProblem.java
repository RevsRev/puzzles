package com.rev.puzzles.euler.framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PeProblem {
    int number();
}
