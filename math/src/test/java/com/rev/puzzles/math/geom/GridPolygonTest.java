package com.rev.puzzles.math.geom;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rev.puzzles.math.geom.DirectionV2.*;
import static org.junit.jupiter.api.Assertions.*;

class GridPolygonTest {

    @Test
    void shouldCreateSquare() {
        final GridPolygon square = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(1, 2)));
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
                ),
                () -> assertEquals(1, square.area())
        );
    }

    @Test
    void shouldCreateRectangle() {
        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(1, 2), new GridPoint(1, 10)));
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
                ),
                () -> assertEquals(9, rectangle.area()));
    }

    @Test
    void shouldCreateRectangleFromOppositeCorners() {
        final GridPolygon rectangle = GridPolygonBuilder.rectangle(new GridPoint(1, 2), new GridPoint(4, 10), true);
        assertAll(
                () -> assertEquals(1, rectangle.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 2), new GridPoint(1, 11)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 11), new GridPoint(5, 11)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 11), new GridPoint(5, 2)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 2), new GridPoint(1, 2)), DOWN)
                        ),
                        rectangle.sides
                ),
                () -> assertEquals(36, rectangle.area()));
    }

    @Test
    void shouldCreateLShape() {
        final GridPolygon lPolygon = GridPolygonBuilder.createFromGridSquareCorners(
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
                ),
                () -> assertEquals(7, lPolygon.area()));
    }

    @Test
    void shouldCreateLShapeOppositeOrder() {
        final GridPolygon lPolygon = GridPolygonBuilder.createFromGridSquareCorners(
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
                ),
                () -> assertEquals(7, lPolygon.area()));
    }

    @Test
    void shouldCreaterShape() {
        final GridPolygon rPolygon = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 0),
                        new GridPoint(0, 6),
                        new GridPoint(4, 6)
                )
        );

        assertAll(
                () -> assertEquals(1, rPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 7)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 7), new GridPoint(5, 7)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 7), new GridPoint(5, 6)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 6), new GridPoint(1, 6)), DOWN),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 6), new GridPoint(1, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        rPolygon.sides
                ),
                () -> assertEquals(11, rPolygon.area()));
    }

    @Test
    void shouldCreate7Shape() {
        final GridPolygon sevenPolygon = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 0),
                        new GridPoint(0, 4),
                        new GridPoint(-10, 4)
                )
        );

        assertAll(
                () -> assertEquals(1, sevenPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-10, 4), new GridPoint(-10, 5)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-10, 5), new GridPoint(1, 5)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 5), new GridPoint(1, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 0), new GridPoint(0, 0)), DOWN),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 4)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 4), new GridPoint(-10, 4)), DOWN)
                        ),
                        sevenPolygon.sides
                ),
                () -> assertEquals(15, sevenPolygon.area()));
    }

    @Test
    void shouldCreateBackwardsLShape() {
        final GridPolygon backwardsLPolygon = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(-6, 0),
                        new GridPoint(-1, 0),
                        new GridPoint(-1, 3)
                )
        );


        assertAll(
                () -> assertEquals(1, backwardsLPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-6, 0), new GridPoint(-6, 1)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-6, 1), new GridPoint(-1, 1)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-1, 1), new GridPoint(-1, 4)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(-1, 4), new GridPoint(0, 4)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 4), new GridPoint(0, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(-6, 0)), DOWN)
                        ),
                        backwardsLPolygon.sides
                ),
                () -> assertEquals(9, backwardsLPolygon.area()));
    }

    @Test
    void shouldCreateUShape() {
        final GridPolygon uShape = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 2),
                        new GridPoint(0, 0),
                        new GridPoint(4, 0),
                        new GridPoint(4, 2)
                )
        );

        assertAll(
                () -> assertEquals(1, uShape.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 3)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 3), new GridPoint(1, 3)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 3), new GridPoint(1, 1)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 1), new GridPoint(4, 1)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(4, 1), new GridPoint(4, 3)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(4, 3), new GridPoint(5, 3)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 3), new GridPoint(5, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        uShape.sides
                ),
                () -> assertEquals(9, uShape.area()));
    }

    @Test
    void shouldCreateEnclosedRectangle() {
        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(
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
                ),
                () -> assertEquals(54, rectangle.area()));
    }

    @Test
    void shouldCreateEnclosedRectangleWhenPointsGivenInDifferentOrder() {
        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(
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
                ),
                () -> assertEquals(54, rectangle.area()));
    }

    @Test
    void shouldCreateEnclosedRectangleWhenPointsAreGivenAntiClockwise() {
        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(
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
                ),
                () -> assertEquals(54, rectangle.area()));
    }

    @Test
    void test1byNRectangleContains() {
        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(1, 2), new GridPoint(1, 10)));

        assertAll(
                //exterior points
                () -> assertFalse(rectangle.contains(new GridPoint(0, 2))),
                () -> assertFalse(rectangle.contains(new GridPoint(-10, 4))),
                () -> assertFalse(rectangle.contains(new GridPoint(0, 11))),
                () -> assertFalse(rectangle.contains(new GridPoint(2, -8))),

                //Interior points
                () -> assertTrue(rectangle.contains(new GridPoint(1, 2))),
                () -> assertTrue(rectangle.contains(new GridPoint(2, 2))),
                () -> assertTrue(rectangle.contains(new GridPoint(2, 7))),
                () -> assertTrue(rectangle.contains(new GridPoint(1, 7))),
                () -> assertTrue(rectangle.contains(new GridPoint(2, 11))),
                () -> assertTrue(rectangle.contains(new GridPoint(1, 11)))
        );
    }

    @Test
    void testMByNRectangleContains() {
        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(8, 0),
                        new GridPoint(0, 0),
                        new GridPoint(0, 5),
                        new GridPoint(8, 5),
                        new GridPoint(8, 0)
                )
        );

        assertAll(
                //exterior points
                () -> assertFalse(rectangle.contains(new GridPoint(-1, 2))),
                () -> assertFalse(rectangle.contains(new GridPoint(-10, 4))),
                () -> assertFalse(rectangle.contains(new GridPoint(0, 11))),
                () -> assertFalse(rectangle.contains(new GridPoint(2, -8))),

                //Interior points
                () -> assertTrue(rectangle.contains(new GridPoint(0, 0))),
                () -> assertTrue(rectangle.contains(new GridPoint(2, 2))),
                () -> assertTrue(rectangle.contains(new GridPoint(4, 1))),
                () -> assertTrue(rectangle.contains(new GridPoint(6, 5))),
                () -> assertTrue(rectangle.contains(new GridPoint(1, 6))),
                () -> assertTrue(rectangle.contains(new GridPoint(8, 3))),
                () -> assertTrue(rectangle.contains(new GridPoint(8, 6)))
        );
    }

    @Test
    void testUShapeContains() {
        final GridPolygon lPolygon = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 2),
                        new GridPoint(0, 0),
                        new GridPoint(4, 0),
                        new GridPoint(4, 2)
                )
        );

        assertAll(
                //exterior points
                () -> assertFalse(lPolygon.contains(new GridPoint(-1, 2)), "Expected to not contain (x,y) = (-1, 2) but did"),
                () -> assertFalse(lPolygon.contains(new GridPoint(-1, 3)), "Expected to not contain (x,y) = (-1, 3) but did"),
                () -> assertFalse(lPolygon.contains(new GridPoint(3, 2)), "Expected to not contain (x,y) = (3, 2) but did"),
                () -> assertFalse(lPolygon.contains(new GridPoint(3, 3)), "Expected to not contain (x,y) = (3, 3) but did"),
                () -> assertFalse(lPolygon.contains(new GridPoint(8, 2)), "Expected to not contain (x,y) = (8, 2) but did"),
                () -> assertFalse(lPolygon.contains(new GridPoint(8, 3)), "Expected to not contain (x,y) = (8, 3) but did"),
                () -> assertFalse(lPolygon.contains(new GridPoint(8, 5)), "Expected to not contain (x,y) = (8, 5) but did"),
                () -> assertFalse(lPolygon.contains(new GridPoint(8, 0)), "Expected to not contain (x,y) = (8, 0) but did"),

                //Interior points
                () -> assertTrue(lPolygon.contains(new GridPoint(0, 0)), "Expected to contain (x,y) = (0, 0) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(0, 1)), "Expected to contain (x,y) = (0, 1) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(0, 2)), "Expected to contain (x,y) = (0, 2) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(1, 2)), "Expected to contain (x,y) = (1, 2) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(1, 1)), "Expected to contain (x,y) = (1, 1) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(2, 1)), "Expected to contain (x,y) = (2, 1) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(3, 1)), "Expected to contain (x,y) = (3, 1) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(4, 1)), "Expected to contain (x,y) = (4, 1) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(4, 2)), "Expected to contain (x,y) = (4, 2) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(4, 3)), "Expected to contain (x,y) = (4, 3) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(5, 3)), "Expected to contain (x,y) = (5, 3) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(5, 2)), "Expected to contain (x,y) = (5, 2) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(5, 1)), "Expected to contain (x,y) = (5, 1) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(5, 0)), "Expected to contain (x,y) = (5, 0) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(4, 0)), "Expected to contain (x,y) = (4, 0) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(3, 0)), "Expected to contain (x,y) = (3, 0) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(2, 0)), "Expected to contain (x,y) = (2, 0) but did not"),
                () -> assertTrue(lPolygon.contains(new GridPoint(1, 0)), "Expected to contain (x,y) = (1, 0) but did not")
        );

        assertAll(
                () -> assertEquals(1, lPolygon.windingNumber),
                () -> assertEquals(
                        List.of(
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 0), new GridPoint(0, 3)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(0, 3), new GridPoint(1, 3)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 3), new GridPoint(1, 1)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(1, 1), new GridPoint(4, 1)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(4, 1), new GridPoint(4, 3)), LEFT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(4, 3), new GridPoint(5, 3)), UP),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 3), new GridPoint(5, 0)), RIGHT),
                                new GridPolygon.PolygonSide(GridSide.create(new GridPoint(5, 0), new GridPoint(0, 0)), DOWN)
                        ),
                        lPolygon.sides
                ));
    }

    @Test
    void testOneRectangleIsInteriorOfAnother() {
        final GridPolygon interior = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(1, 2), new GridPoint(1, 10)));
        final GridPolygon exterior = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(1, 2), new GridPoint(1, 15)));
        assertAll(
                () -> assertTrue(interior.isInteriorOf(exterior)),
                () -> assertFalse(exterior.isInteriorOf(interior))
        );
    }

    @Test
    void testDisjointRectanglesAreNotInteriorOfEachOther() {
        final GridPolygon first = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(1, 2), new GridPoint(1, 10)));
        final GridPolygon second = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(1, 11), new GridPoint(1, 15)));
        assertAll(
                () -> assertFalse(first.isInteriorOf(second)),
                () -> assertFalse(second.isInteriorOf(first))
        );
    }

    @Test
    void testRectangleIsInteriorOfUShape() {
        final GridPolygon uShape = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 2),
                        new GridPoint(0, 0),
                        new GridPoint(4, 0),
                        new GridPoint(4, 2)
                )
        );

        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(List.of(new GridPoint(0, 0), new GridPoint(0, 2)));
        assertAll(
                () -> assertTrue(rectangle.isInteriorOf(uShape)),
                () -> assertFalse(uShape.isInteriorOf(rectangle))
        );
    }

    @Test
    void testShapeIsInteriorOfItself() {
        final GridPolygon uShape = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 2),
                        new GridPoint(0, 0),
                        new GridPoint(4, 0),
                        new GridPoint(4, 2)
                )
        );

        assertTrue(uShape.isInteriorOf(uShape));
    }

    @Test
    void testUShapeIsInteriorOfBigRectangle() {
        final GridPolygon uShape = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 2),
                        new GridPoint(0, 0),
                        new GridPoint(4, 0),
                        new GridPoint(4, 2)
                )
        );

        final GridPolygon rectangle = GridPolygonBuilder.createFromGridSquareCorners(
                List.of(
                        new GridPoint(0, 0),
                        new GridPoint(0, 5),
                        new GridPoint(8, 5),
                        new GridPoint(8, 0),
                        new GridPoint(0, 0)
                )
        );

        assertAll(
                () -> assertFalse(rectangle.isInteriorOf(uShape)),
                () -> assertTrue(uShape.isInteriorOf(rectangle))
        );
    }
}