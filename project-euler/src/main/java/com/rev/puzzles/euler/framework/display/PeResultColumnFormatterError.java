package com.rev.puzzles.euler.framework.display;

import com.rev.puzzles.euler.framework.PeCoordinate;
import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public final class PeResultColumnFormatterError extends ResultColumnFormatter<PeCoordinate> {
    public PeResultColumnFormatterError(final String header, final int width, final char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<PeCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            if (result.getError().get().getCause() != null) {
                final String message = result.getError().get().getCause().getMessage();
                if (message == null || message.isBlank()) {
                    return result.getError().get().getCause().toString();
                }
                return message;
            }
            return result.getError().get().toString();
        }
        return "";
    }
}
