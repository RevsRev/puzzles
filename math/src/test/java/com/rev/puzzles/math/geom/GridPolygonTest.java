package com.rev.puzzles.math.geom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rev.puzzles.math.geom.DirectionV2.*;
import static com.rev.puzzles.math.geom.DirectionV2.DOWN;
import static org.junit.jupiter.api.Assertions.*;

class GridPolygonTest {

    @Test
    void shouldCreateSquare() {
        final GridPolygon square = GridPolygon.createFromGridSquareCorners(List.of(new GridPoint(1, 2)));
        Assertions.assertEquals(
                List.of(
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 2), new GridPoint(1, 3)), LEFT),
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 3), new GridPoint(2, 3)), UP),
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 3), new GridPoint(2, 2)), RIGHT),
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 2), new GridPoint(1, 2)), DOWN)
                ),
                square.sides
        );
    }

    @Test
    void shouldCreateRectangle() {
        final GridPolygon rectangle = GridPolygon.createFromGridSquareCorners(List.of(new GridPoint(1, 2), new GridPoint(1, 10)));
        Assertions.assertEquals(
                List.of(
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 2), new GridPoint(1, 11)), LEFT),
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 11), new GridPoint(2, 11)), UP),
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 11), new GridPoint(2, 2)), RIGHT),
                        new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 2), new GridPoint(1, 2)), DOWN)
                ),
                rectangle.sides
        );
    }
}