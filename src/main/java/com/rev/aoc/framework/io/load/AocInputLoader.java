package com.rev.aoc.framework.io.load;

import com.rev.aoc.framework.aoc.AocCoordinate;

import java.io.IOException;
import java.util.List;

public interface AocInputLoader {
    List<String> load(AocCoordinate coordinate) throws IOException;

    static String pad(final int day) {
        if (day < 10) {
            return "0" + day;
        }
        return "" + day;
    }
}
