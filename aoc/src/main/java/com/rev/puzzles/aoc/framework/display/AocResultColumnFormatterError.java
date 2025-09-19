package com.rev.puzzles.aoc.framework.display;

import com.rev.puzzles.aoc.framework.AocCoordinate;
import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public final class AocResultColumnFormatterError extends ResultColumnFormatter<AocCoordinate> {
    public AocResultColumnFormatterError(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<AocCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            if (result.getError().get().getCause() != null) {
                String message = result.getError().get().getCause().getMessage();
                if (message == null) {
                    return result.getError().get().getCause().toString();
                }
                return message;
            }
            return result.getError().get().toString();
        }
        return "";
    }
}
