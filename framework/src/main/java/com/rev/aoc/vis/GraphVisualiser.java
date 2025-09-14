package com.rev.aoc.vis;

import org.jgrapht.Graph;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class GraphVisualiser<V, E> {

    private final DOTExporter<V, E> exporter;

    public GraphVisualiser(final DOTExporter<V, E> dotExporter) {
        this.exporter = dotExporter;
    }

    public void visualise(final Graph<V, E> graph) throws VisualisationException {
        try {
            visualiseThrowing(graph);
        } catch (Exception e) {
            if (e instanceof VisualisationException) {
                throw e;
            }
            throw new VisualisationException(e);
        }
    }

    public void visualiseThrowing(final Graph<V, E> graph) throws VisualisationException {
        Date date = new Date();

        String dotFilePath = getTempFilePath(date, "dot");
        exporter.exportGraph(graph, new File(dotFilePath));

        String svgFilePath = getTempFilePath(date, "svg");
        exportSvgUsingDot(dotFilePath, svgFilePath);
        displayImage(svgFilePath);
        deleteTempFile(dotFilePath);
        deleteTempFile(svgFilePath);
    }

    private static void deleteTempFile(final String dotFilePath) throws VisualisationException {
        try {
            Files.delete(Path.of(dotFilePath));
        } catch (IOException e) {
            throw new VisualisationException(e);
        }
    }

    private void displayImage(final String imageFilePath) throws VisualisationException {
        ProcessBuilder pBuilder = new ProcessBuilder();
        pBuilder.command("eog", imageFilePath);
        Process p = null;
        try {
            p = pBuilder.start();
            p.waitFor();
            checkExitValue(p);
        } catch (Exception e) {
            throw new VisualisationException(e);
        }
    }

    private void exportSvgUsingDot(final String dotFilePath, final String svgFilePath)
            throws VisualisationException {
        ProcessBuilder pBuilder = new ProcessBuilder();
        pBuilder.command("dot", "-Tsvg", dotFilePath);
        pBuilder.redirectOutput(ProcessBuilder.Redirect.to(new File(svgFilePath)));
        try {
            Process p = pBuilder.start();
            p.waitFor();
            checkExitValue(p);
        } catch (Exception e) {
            throw new VisualisationException(e);
        }
    }

    private void checkExitValue(final Process p) throws VisualisationException {
        if (p.exitValue() != 0) {
            String msg = String.format("Process '%s' exited with value '%s'", p, p.exitValue());
            throw new VisualisationException(msg);
        }
    }

    private static String getTempFilePath(final Date date, final String extension) {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd:hhmmss");
        return String.format("%s/%s.%s", System.getProperty("java.io.tempdir"), format.format(date), extension);
    }

}
