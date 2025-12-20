package com.rev.puzzles.math.geom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridPolygonTest {

    @Test
    void shouldCreateSquare() {
        final GridPolygon square = GridPolygon.createFromGridSquareCorners(List.of(new GridPoint(1, 2)));
        Assertions.assertEquals(
                List.of(
                        GridSide.create(new GridPoint(1, 2), new GridPoint(1, 3)),
                        GridSide.create(new GridPoint(1, 3), new GridPoint(2, 3)),
                        GridSide.create(new GridPoint(2, 3), new GridPoint(2, 2)),
                        GridSide.create(new GridPoint(2, 2), new GridPoint(1, 2))
                ),
                square.sides
        );
    }
}