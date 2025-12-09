package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.geom.Point;
import com.rev.puzzles.math.geom.PointPolygon;
import com.rev.puzzles.math.geom.PointSide;
import org.jgrapht.alg.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class D09
{

    @AocProblemI(year = 2025, day = 9, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader)
    {

        final List<Pair<Long, Long>> redTiles = resourceLoader.resources().stream().map(
                s -> {
                    final String[] split = s.replaceAll("\\s+", "").split(",");
                    return Pair.of(
                            Long.parseLong(split[0]),
                            Long.parseLong(split[1])
                    );
                }
        ).toList();

        long maxArea = 0;

        for (int i = 0; i < redTiles.size(); i++)
        {
            for (int j = i; j < redTiles.size(); j++)
            {
                final Pair<Long, Long> first = redTiles.get(i);
                final Pair<Long, Long> second = redTiles.get(j);
                final long leftX = Math.min(first.getFirst(), second.getFirst());
                final long rightX = Math.max(first.getFirst(), second.getFirst());
                final long bottomY = Math.min(first.getSecond(), second.getSecond());
                final long topY = Math.max(first.getSecond(), second.getSecond());

                final long area = (rightX - leftX + 1) * (topY - bottomY + 1);
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    @AocProblemI(year = 2025, day = 9, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader)
    {
        final List<Point> redtiles = new ArrayList<>(resourceLoader.resources().stream().map(
                s -> {
                    final String[] split = s.replaceAll("\\s+", "").split(",");
                    return new Point(
                            Long.parseLong(split[0]),
                            Long.parseLong(split[1])
                    );
                }
        ).toList());

        final List<PointSide> polygonSides = new ArrayList<>();

        for (int i = 0; i < redtiles.size(); i++) {
            final Point first = redtiles.get(i);
            final Point second = redtiles.get((i + 1) % redtiles.size());
            final PointSide pointSide = PointSide.of(first, second);
            polygonSides.add(pointSide);
        }

        final PointPolygon pointPolygon = PointPolygon.create(polygonSides);

        final List<PointPolygon> rectangles = new ArrayList<>();
        for (int i = 0; i < redtiles.size(); i++)
        {
            for (int j = i + 1; j < redtiles.size(); j++)
            {
                final Point first = redtiles.get(i);
                final Point second = redtiles.get(j);
                rectangles.add(PointPolygon.rectangle(first, second));
            }
        }

        long biggestArea = 0;
        for (final PointPolygon rectangle : rectangles) {
            if (!rectangle.isInteriorOf(pointPolygon)) {
                continue;
            }

            final Iterator<PointSide> iterator = rectangle.sides().iterator();
            PointSide next = iterator.next();
            long xMin = Math.min(next.start().x(), next.end().x());
            long xMax = Math.max(next.start().x(), next.end().x());
            long yMin = Math.min(next.start().y(), next.end().y());
            long yMax = Math.max(next.start().y(), next.end().y());
            while (iterator.hasNext()) {
                next = iterator.next();
                xMin = Math.min(xMin, Math.min(next.start().x(), next.end().x()));
                xMax = Math.max(xMax, Math.max(next.start().x(), next.end().x()));
                yMin = Math.min(yMin, Math.min(next.start().y(), next.end().y()));
                yMax = Math.max(yMax, Math.max(next.start().y(), next.end().y()));
            }

            final long area = (xMax - xMin + 1) * (yMax - yMin + 1);
            biggestArea = Math.max(biggestArea, area);
        }

        return biggestArea;
    }
}
