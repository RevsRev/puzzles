package com.rev.aoc.framework.aoc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AocVisualisation {
    int year();
    int day();
    int part();
}
