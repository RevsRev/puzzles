package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.vis.GraphVisualiser;
import com.rev.aoc.vis.VisualisationException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class D24 extends AocProblem<Long, Long> {

    private static final long BIT_FLAG = 1;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 24);
    }

    @AocProblemI(year = 2024, day = 24, part = 1)
    @Override
    protected Long partOneImpl() {
        List<String> lines = loadResources();
        TreeMap<String, Long> registerValues = new TreeMap<>();
        int start = 0;
        while (start < lines.size() && !lines.get(start).isEmpty()) {
            String[] split = lines.get(start).replaceAll("\\s+", "").split(":");
            String inputName = split[0];
            long inputValue = Long.parseLong(split[1]);
            registerValues.put(inputName, inputValue);
            start++;
        }
        lines.subList(0, start);

        start++;
        Map<String, String[]> targetToInstructionMap = new HashMap<>();
        List<String[]> instructions = new ArrayList<>(lines.size() - start);
        List<String> targets = new ArrayList<>(lines.size() - start);
        while (start < lines.size() && !lines.get(start).isEmpty()) {
            String[] split = lines.get(start).split("->");
            String target = split[1].replaceAll("\\s+", "");
            String[] instruction = split[0].trim().split(" ");
            instructions.add(instruction);
            targets.add(target);
            targetToInstructionMap.put(target, instruction);
            start++;
        }

        while (!targetToInstructionMap.isEmpty()) {
            Iterator<Map.Entry<String, String[]>> it = targetToInstructionMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String[]> entry = it.next();
                String[] instruction = entry.getValue();
                String left = instruction[0];
                String right = instruction[2];
                if (!registerValues.containsKey(left) || !registerValues.containsKey(right)) {
                    continue;
                }
                String target = entry.getKey();
                String operation = instruction[1];
                long result = processInstruction(registerValues, left, operation, right);
                registerValues.put(target, result);
                it.remove();
            }
        }

        Iterator<String> it = registerValues.descendingKeySet().iterator();
        long result = 0;
        while (it.hasNext()) {
            String val = it.next();
            if (!val.startsWith("z")) {
                break;
            }
            result = result << 1;
            result += registerValues.get(val) & BIT_FLAG;
        }
        return result;
    }

    @AocProblemI(year = 2024, day = 24, part = 2)
    @Override
    protected Long partTwoImpl() {

        //cqk,fph,gds,jrs,wrk,z15,z21,z34
        return null;
    }

    @Override
    public void visualiseProblem() throws VisualisationException {

        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        List<String> lines = loadResources();
        TreeMap<String, Long> registerValues = new TreeMap<>();
        int start = 0;
        while (start < lines.size() && !lines.get(start).isEmpty()) {
            String[] split = lines.get(start).replaceAll("\\s+", "").split(":");
            String inputName = split[0];
            long inputValue = Long.parseLong(split[1]);
            registerValues.put(inputName, inputValue);
            start++;
        }
        lines.subList(0, start);

        start++;
        long id = 0;
        List<String[]> instructions = new ArrayList<>(lines.size() - start);
        List<String> targets = new ArrayList<>(lines.size() - start);
        Map<String, String> edgeToOperation = new HashMap<>();
        while (start < lines.size() && !lines.get(start).isEmpty()) {
            String[] split = lines.get(start).split("->");
            String target = split[1].replaceAll("\\s+", "");
            String[] instruction = split[0].trim().split(" ");
            String left = instruction[0];
            String operation = instruction[1] + id;
            String right = instruction[2];
            graph.addVertex(target);
            graph.addVertex(left);
            graph.addVertex(operation);
            graph.addVertex(right);

            graph.addEdge(left, operation, new DefaultEdge());
            graph.addEdge(right, operation, new DefaultEdge());
            graph.addEdge(operation, target);
            id++;
            start++;
        }


        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(s -> s);
//        exporter.setVertexAttributeProvider(s -> );
//        exporter.setEdgeAttributeProvider();
        exporter.setVertexAttributeProvider(this::getColorMap);
        GraphVisualiser<String, DefaultEdge> visualiser = new GraphVisualiser<>(exporter);
        visualiser.visualise(graph);
    }

    private Map<String, Attribute> getColorMap(final String s) {
        Map<String, Attribute> colorMap = new HashMap<>();
        colorMap.put("style", DefaultAttribute.createAttribute("filled"));
        String color = "white";
        if (s.contains("XOR")) {
            color = "orange";
        } else if (s.contains("AND")) {
            color = "darkslategray2";
        } else if (s.contains("OR")) {
            color = "mediumpurple1";
        }
        colorMap.put("color", DefaultAttribute.createAttribute(color));
        return colorMap;
    }

    private long processInstruction(final TreeMap<String, Long> registerValues,
                                    final String left,
                                    final String operation,
                                    final String right) {
        registerValues.computeIfAbsent(left, k -> 0L);
        registerValues.computeIfAbsent(right, k -> 0L);

        long l = registerValues.get(left);
        long r = registerValues.get(right);

        switch (operation) {
            case "XOR":
                return (l ^ r) & BIT_FLAG;
            case "AND":
                return (l & r) & BIT_FLAG;
            default:
        }
        return (l | r) & BIT_FLAG;

    }
}
