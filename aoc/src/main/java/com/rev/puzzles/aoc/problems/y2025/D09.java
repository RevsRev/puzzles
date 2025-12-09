package com.rev.puzzles.aoc.problems.y2025;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.Comparators;
import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.framework.util.geom.Direction;
import com.rev.puzzles.math.linalg.vec.Vec3;

import org.jgrapht.alg.util.Pair;

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
        final List<Pair<Long, Long>> redtiles = new ArrayList<>(resourceLoader.resources().stream().map(
                s -> {
                    final String[] split = s.replaceAll("\\s+", "").split(",");
                    return Pair.of(
                            Long.parseLong(split[0]),
                            Long.parseLong(split[1])
                    );
                }
        ).toList());

        final List<Pair<Long,Long>> redTilesXYSorted = new ArrayList<>(redtiles);
        final List<Pair<Long,Long>> redTilesYXSorted = new ArrayList<>(redtiles);
        redTilesYXSorted.sort(Comparator.comparingLong(Pair<Long,Long>::getSecond).thenComparingLong(Pair::getFirst));
        redTilesXYSorted.sort(Comparator.comparingLong(Pair<Long,Long>::getFirst).thenComparing(Pair::getSecond));

        final long width = redTilesXYSorted.getLast().getFirst() - redTilesXYSorted.getFirst().getFirst();
        final long depth = redTilesYXSorted.getLast().getSecond() - redTilesYXSorted.getFirst().getSecond();
        System.out.println("Width :" + width);
        System.out.println("Depth :" + depth);
        System.out.println("Area :" + width * depth);

        Set<Pair<Long, Long>> boundary = new LinkedHashSet<>();
        int windingNumber = 0;
        Direction direction = null;

        for (int i = 0; i <= redtiles.size(); i++)
        {
            final Pair<Long, Long> first = redtiles.get(i % redtiles.size());
            final Pair<Long, Long> second = redtiles.get((i + 1) % redtiles.size());

            final long xChange = (second.getFirst() - first.getFirst());
            final long yChange = (second.getSecond() - first.getSecond());

            final Direction nextDirection = Direction.of(
                    (int)xChange / (int)Math.max(1, Math.abs(xChange)),
                    (int)yChange / (int)Math.max(1, Math.abs(yChange))
            );

            if (direction != null)
            {
                if (nextDirection == direction.next())
                {
                    windingNumber += 1;
                }
                else if (nextDirection == direction.previous())
                {
                    windingNumber -= 1;
                }
                else
                {
                    throw new ProblemExecutionException("Failed. Is the input what we expect?");
                }
                direction = nextDirection;
            }
            else
            {
                direction = nextDirection;
                windingNumber = 0;
            }

            if (xChange == 0)
            {
                if (yChange > 0)
                {
                    for (long j = first.getSecond(); j <= second.getSecond(); j++)
                    {
                        boundary.add(Pair.of(first.getFirst(), j));
                    }
                }
                else
                {
                    for (long j = first.getSecond(); j >= second.getSecond(); j--)
                    {
                        boundary.add(Pair.of(first.getFirst(), j));
                    }
                }
            }

            if (yChange == 0)
            {
                if (xChange > 0)
                {
                    for (long j = first.getFirst(); j <= second.getFirst(); j++)
                    {
                        boundary.add(Pair.of(j, first.getSecond()));
                    }
                }
                else
                {
                    for (long j = first.getFirst(); j >= second.getFirst(); j--)
                    {
                        boundary.add(Pair.of(j, first.getSecond()));
                    }
                }
            }
        }

        Set<Pair<Long, Long>> loopBoundaryAndInterior = new HashSet<>();
        //direction is now the initial direction, and we know the winding number

        boolean left = windingNumber < 0;

        if (!left) {
            throw new ProblemExecutionException("Need to make this code more generic!");
        }

        //go over the loop again
        for (int i = 0; i < redtiles.size(); i++)
        {
            System.out.printf("Considering tile boundary: %s/%s%n", i, redtiles.size());
            final Pair<Long, Long> first = redtiles.get(i % redtiles.size());
            final Pair<Long, Long> second = redtiles.get((i + 1) % redtiles.size());

            final long xChange = (second.getFirst() - first.getFirst());
            final long yChange = (second.getSecond() - first.getSecond());

            final Direction nextDirection = Direction.of(
                    (int)xChange / (int)Math.max(1, Math.abs(xChange)),
                    (int)yChange / (int)Math.max(1, Math.abs(yChange))
            );

            if (xChange == 0)
            {
                if (yChange > 0)
                {
                    //going up, look left
                    for (long j = first.getSecond(); j <= second.getSecond(); j++)
                    {
                        long x = 0;
                        Pair<Long, Long> interior = Pair.of(first.getFirst() - x, j);
                        do {
//                            System.out.println("Looking left: " + interior);
                            loopBoundaryAndInterior.add(interior);
                            x++;
                            interior = Pair.of(first.getFirst() - x, j);
                        } while (!boundary.contains(interior));
                    }
                }
                else
                {
                    //going down, look right
                    for (long j = first.getSecond(); j >= second.getSecond(); j--)
                    {
                        long x = 0;
                        Pair<Long, Long> interior = Pair.of(first.getFirst() + x, j);
                        do {
//                            System.out.println("Looking right: " + interior);
                            loopBoundaryAndInterior.add(interior);
                            x++;
                            interior = Pair.of(first.getFirst() + x, j);
                        } while (!boundary.contains(interior));
                    }
                }
            }

            if (yChange == 0)
            {
                if (xChange > 0)
                {
                    //going right, look up
                    for (long j = first.getFirst(); j <= second.getFirst(); j++)
                    {
                        long x = 0;
                        Pair<Long, Long> interior = Pair.of(j, first.getSecond() + x);
                        do {
//                            System.out.println("Looking up: " + interior);
                            loopBoundaryAndInterior.add(interior);
                            x++;
                            interior = Pair.of(j, first.getSecond() + x);
                        } while (!boundary.contains(interior));
                    }
                }
                else
                {
                    //going left, look down
                    for (long j = first.getFirst(); j >= second.getFirst(); j--)
                    {
                        long x = 0;
                        Pair<Long, Long> interior = Pair.of(j, first.getSecond() - x);
                        do {
//                            System.out.println("Looking down: " + interior);
                            loopBoundaryAndInterior.add(interior);
                            x++;
                            interior = Pair.of(j, first.getSecond() - x);
                        } while (!boundary.contains(interior));
                    }
                }
            }
        }

        System.out.println("" + windingNumber);

        return -1L;
    }
}
