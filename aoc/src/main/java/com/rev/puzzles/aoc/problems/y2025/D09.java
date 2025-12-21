package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.aoc.framework.AocVisualisation;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.geom.GridPoint;
import com.rev.puzzles.math.geom.GridPolygon;
import com.rev.puzzles.math.geom.GridPolygonBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public final class D09 {

    @AocProblemI(year = 2025, day = 9, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {

        final List<GridPoint> redTiles = getGridPoints(resourceLoader);

        long maxArea = 0;

        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = i; j < redTiles.size(); j++) {
                final GridPolygon rectangle = GridPolygonBuilder.rectangle(redTiles.get(i), redTiles.get(j), true);
                maxArea = Math.max(maxArea, rectangle.area());
            }
        }

        return maxArea;
    }

    @AocProblemI(year = 2025, day = 9, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<GridPoint> redTiles = getGridPoints(resourceLoader);

        final List<GridPoint> loopedList = new ArrayList<>(redTiles);
        loopedList.add(redTiles.get(0));

        final GridPolygon polygon = GridPolygonBuilder.createFromGridSquareCorners(loopedList);

        long maxArea = 0;
        for (int i = 0; i < loopedList.size(); i++) {
            for (int j = i; j < loopedList.size(); j++) {
                final GridPolygon rectangle = GridPolygonBuilder.rectangle(loopedList.get(i), loopedList.get(j), true);
                final long area = rectangle.area();
                if (area > maxArea && rectangle.isInteriorOf(polygon)) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }

    @AocVisualisation(year = 2025, day = 9, part = 2)
    public void visualise(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<GridPoint> redTiles = getGridPoints(resourceLoader);

        final List<GridPoint> loopedList = new ArrayList<>(redTiles);
        loopedList.add(redTiles.get(0));

        final GridPolygon polygon = GridPolygonBuilder.createFromGridSquareCorners(loopedList);

        long maxArea = 0;
        GridPolygon maxRectangle = null;

        for (int i = 0; i < loopedList.size(); i++) {
            for (int j = i; j < loopedList.size(); j++) {
                final GridPolygon rectangle = GridPolygonBuilder.rectangle(loopedList.get(i), loopedList.get(j), true);
                final long area = rectangle.area();
                if (area > maxArea && rectangle.isInteriorOf(polygon)) {
                    maxArea = area;
                    maxRectangle = rectangle;
                }
            }
        }


        final XYSeries polygonSeries = getXySeries(polygon, "polygon");
        final XYSeries rectangleSeries = getXySeries(maxRectangle, "rectangle");

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(rectangleSeries);
        dataset.addSeries(polygonSeries);

        final JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                "Polygon",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesLinesVisible(1, true);

        renderer.setSeriesVisible(0, true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesVisible(1, true);
        renderer.setSeriesPaint(1, Color.RED);

        final ChartFrame frame = new ChartFrame("Plot", xyLineChart);
        frame.pack();
        frame.setVisible(true);
    }

    private static List<GridPoint> getGridPoints(final ProblemResourceLoader<List<String>> resourceLoader) {
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

    private static XYSeries getXySeries(final GridPolygon shape, final String name) {
        final XYSeries polygonSeries = new XYSeries(name, false, true);
        for (GridPolygon.PolygonSide side : shape.getSides()) {
            polygonSeries.addOrUpdate(side.side().start().x(), side.side().start().y());
            polygonSeries.addOrUpdate(side.side().end().x(), side.side().end().y());
        }
        return polygonSeries;
    }
}
