package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.geom.GridPoint;
import com.rev.puzzles.math.geom.GridPolygon;
import com.rev.puzzles.math.geom.GridPolygonBuilder;

import java.util.ArrayList;
import java.util.List;

public final class D09
{

    @AocProblemI(year = 2025, day = 9, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader)
    {

        final List<GridPoint> redTiles = getGridPoints(resourceLoader);

        long maxArea = 0;

        for (int i = 0; i < redTiles.size(); i++)
        {
            for (int j = i; j < redTiles.size(); j++)
            {
                final GridPolygon rectangle = GridPolygonBuilder.rectangle(redTiles.get(i), redTiles.get(j), true);
                maxArea = Math.max(maxArea, rectangle.area());
            }
        }

        return maxArea;
    }

    @AocProblemI(year = 2025, day = 9, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader)
    {

        final List<GridPoint> redTiles = getGridPoints(resourceLoader);

        final List<GridPoint> loopedList = new ArrayList<>(redTiles);
        loopedList.add(redTiles.get(0));

        final GridPolygon polygon = GridPolygonBuilder.createFromGridSquareCorners(loopedList);

        long maxArea = 0;

        for (int i = 0; i < loopedList.size(); i++)
        {
            for (int j = i; j < loopedList.size(); j++)
            {
                final GridPolygon rectangle = GridPolygonBuilder.rectangle(loopedList.get(i), loopedList.get(j), true);
                if (rectangle.isInteriorOf(polygon)) {
                    maxArea = Math.max(maxArea, rectangle.area());
                }
            }
        }

        return maxArea;
    }

    private static List<GridPoint> getGridPoints(ProblemResourceLoader<List<String>> resourceLoader) {
        final List<GridPoint> redTiles = resourceLoader.resources().stream().map(
                s -> {
                    final String[] split = s.replaceAll("\\s+", "").split(",");
                    return new GridPoint(
                            Long.parseLong(split[0]),
                            Long.parseLong(split[1])
                    );
                }
        ).toList();
        return redTiles;
    }
}
