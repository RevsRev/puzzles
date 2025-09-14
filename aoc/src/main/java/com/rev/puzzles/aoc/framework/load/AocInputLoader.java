package com.rev.puzzles.aoc.framework.load;


import com.rev.puzzles.aoc.framework.AocCoordinate;

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
