package com.rev.puzzles.math.geom;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rev.puzzles.math.geom.DirectionV2.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GridPolygonTest {

    @Test
    void shouldCreateSquare() {
        final GridPolygon square = GridPolygon.createFromGridSquareCorners(List.of(new GridPoint(1, 2)));
        assertAll(
                () -> assertEquals(1, square.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 2), new GridPoint(1, 3)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 3), new GridPoint(2, 3)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 3), new GridPoint(2, 2)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 2), new GridPoint(1, 2)), DOWN)
                        ),
                        square.sides
                ));
    }

    @Test
    void shouldCreateRectangle() {
        final GridPolygon rectangle = GridPolygon.createFromGridSquareCorners(List.of(new GridPoint(1, 2), new GridPoint(1, 10)));
        assertAll(
                () -> assertEquals(1, rectangle.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 2), new GridPoint(1, 11)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 11), new GridPoint(2, 11)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 11), new GridPoint(2, 2)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(2, 2), new GridPoint(1, 2)), DOWN)
                        ),
                        rectangle.sides
                ));
    }

    @Test
    void shouldCreateLShape() {
        final GridPolygon lPolygon = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 2),
                        new GridPoint(0, 0),
                        new GridPoint(4, 0)
                )
        );

        assertAll(
                () -> assertEquals(1, lPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 3)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 3), new GridPoint(1, 3)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 3), new GridPoint(1, 1)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 1), new GridPoint(5, 1)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 1), new GridPoint(5, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        lPolygon.sides
                ));
    }

    @Test
    void shouldCreateLShapeOppositeOrder() {
        final GridPolygon lPolygon = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(4, 0),
                        new GridPoint(0, 0),
                        new GridPoint(0, 2)
                )
        );

        assertAll(
                () -> assertEquals(1, lPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 3)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 3), new GridPoint(1, 3)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 3), new GridPoint(1, 1)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 1), new GridPoint(5, 1)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 1), new GridPoint(5, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        lPolygon.sides
                ));
    }

    @Test
    void shouldCreaterShape() {
        final GridPolygon lPolygon = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 0),
                        new GridPoint(0, 6),
                        new GridPoint(4, 6)
                )
        );

        assertAll(
                () -> assertEquals(1, lPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 7)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 7), new GridPoint(5, 7)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 7), new GridPoint(5, 6)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 6), new GridPoint(1, 6)), DOWN),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 6), new GridPoint(1, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        lPolygon.sides
                ));
    }

    @Test
    void shouldCreate7Shape() {
        final GridPolygon lPolygon = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 0),
                        new GridPoint(0, 4),
                        new GridPoint(-10, 4)
                )
        );

        assertAll(
                () -> assertEquals(1, lPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-10, 4), new GridPoint(-10, 5)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-10, 5), new GridPoint(1, 5)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 5), new GridPoint(1, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 0), new GridPoint(0, 0)), DOWN),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 4)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 4), new GridPoint(-10, 4)), DOWN)
                        ),
                        lPolygon.sides
                ));
    }

    @Test
    void shouldCreateBackwardsLShape() {
        final GridPolygon lPolygon = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(-6, 0),
                        new GridPoint(-1, 0),
                        new GridPoint(-1, 3)
                )
        );


        assertAll(
                () -> assertEquals(1, lPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-6, 0), new GridPoint(-6, 1)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-6, 1), new GridPoint(-1, 1)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-1, 1), new GridPoint(-1, 4)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-1, 4), new GridPoint(0, 4)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 4), new GridPoint(0, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(-6, 0)), DOWN)
                        ),
                        lPolygon.sides
                ));
    }

    @Test
    void shouldCreateEnclosedRectangle() {
        final GridPolygon rectangle = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 0),
                        new GridPoint(0, 5),
                        new GridPoint(8, 5),
                        new GridPoint(8, 0),
                        new GridPoint(0, 0)
                )
        );


        assertAll(
                () -> assertEquals(1, rectangle.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 6)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 6), new GridPoint(9, 6)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(9, 6), new GridPoint(9, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(9, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        rectangle.sides
                ));
    }

    @Test
    //Not passing at the moment
    @Disabled
    void shouldCreateEnclosedRectangleWhenPointsGivenInDifferentOrder() {
        final GridPolygon rectangle = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(8, 0),
                        new GridPoint(0, 0),
                        new GridPoint(0, 5),
                        new GridPoint(8, 5),
                        new GridPoint(8, 0)
                )
        );


        assertAll(
                () -> assertEquals(1, rectangle.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 6)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 6), new GridPoint(9, 6)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(9, 6), new GridPoint(9, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(9, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        rectangle.sides
                ));
    }

    @Test
    void shouldCreateEnclosedRectangleWhenPointsAreGivenAntiClockwise() {
        final GridPolygon rectangle = GridPolygon.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 0),
                        new GridPoint(8, 0),
                        new GridPoint(8, 5),
                        new GridPoint(0, 5),
                        new GridPoint(0, 0)
                )
        );


        assertAll(
                () -> assertEquals(1, rectangle.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 6)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 6), new GridPoint(9, 6)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(9, 6), new GridPoint(9, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(9, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        rectangle.sides
                ));
    }
}