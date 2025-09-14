package com.rev.aoc.problems.y2023;

import com.rev.aoc.framework.aoc.AocProblemI;
import com.rev.aoc.framework.aoc.AocVisualisation;
import com.rev.aoc.framework.problem.ResourceLoader;
import com.rev.aoc.vis.GraphVisualiser;
import com.rev.aoc.vis.VisualisationException;
import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;

public final class D20 {

    public static final String FLIP_FLOP = "%";
    public static final String CONJUNCTION = "&";
    public static final String BROADCASTER = "broadcaster";
    public static final String BUTTON = "button";

    private static final int NUM_BUTTON_PRESSES = 1000;
    public static final String RX = "rx";

    @AocProblemI(year = 2023, day = 20, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        List<String> strings = resourceLoader.resources();
        Map<String, Module> modules = parseToModules(strings);

        PartOnePulseCounter pulseCounter = new PartOnePulseCounter();
        for (Module m : modules.values()) {
            m.pulseSendConsumer = pulseCounter;
        }
        Module button = modules.get(BUTTON);
        for (int i = 0; i < NUM_BUTTON_PRESSES; i++) {
            start(button);
        }
        return (long) (pulseCounter.numHigh * pulseCounter.numLow);
    }

    @AocProblemI(year = 2023, day = 20, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
//        List<String> strings = resourceLoader.resource();
//        Map<String, Module> modules = parseToModules(strings);
//        Module rx = modules.get(RX);
//        PartTwoLowPulseDetector receiveConsumer = new PartTwoLowPulseDetector();
//        rx.pulseReceiveConsumer = receiveConsumer;
//        Module button = modules.get(BUTTON);
//        long buttonPresses = 0;
//        while (!receiveConsumer.lowReceived) {
//            buttonPresses++;
//            start(button);
//        }
//        return buttonPresses;
        //TODO - Too slow - need a better way!
        return 0L;
    }

    @AocVisualisation(year = 2023, day = 20, part = 1)
    public void visualiseProblem(final ResourceLoader resourceLoader) throws VisualisationException {
        List<String> strings = resourceLoader.resources();
        Map<String, Module> modules = parseToModules(strings);
        Graph<Module, DefaultEdge> graph = asGraph(modules);
        DOTExporter<Module, DefaultEdge> exporter = new DOTExporter<>(Module::getName);
        exporter.setVertexAttributeProvider(vertexAttributeProvider());
        GraphVisualiser<Module, DefaultEdge> visualiser = new GraphVisualiser<>(exporter);
        visualiser.visualise(graph);
    }

    private Function<Module, Map<String, Attribute>> vertexAttributeProvider() {
        return module -> getColorMap(module.getClass());
    }

    private Map<String, Attribute> getColorMap(final Class<? extends Module> moduleClazz) {
        Map<String, Attribute> colorMap = new HashMap<>();
        colorMap.put("style", DefaultAttribute.createAttribute("filled"));
        String color = "white";
        if (Broadcast.class.equals(moduleClazz)) {
            color = "greenyellow";
        } else if (Button.class.equals(moduleClazz)) {
            color = "darkslategray2";
        } else if (Output.class.equals(moduleClazz)) {
            color = "mediumpurple1";
        } else if (FlipFlop.class.equals(moduleClazz)) {
            color = "orange";
        } else if (Conjunction.class.equals(moduleClazz)) {
            color = "yellow";
        }
        colorMap.put("color", DefaultAttribute.createAttribute(color));
        return colorMap;
    }

    private Graph<Module, DefaultEdge> asGraph(final Map<String, Module> modules) {
        Graph<Module, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for (Module m : modules.values()) {
            graph.addVertex(m);
        }

        for (Module m : modules.values()) {
            for (Module target : m.outputs) {
                graph.addEdge(m, target);
            }
        }
        return graph;
    }

    public void start(final Module m) {
        Queue<Runnable> runQueue = new LinkedList<>();
        runQueue.add(() -> m.receive(null, Pulse.LOW, runQueue));
        while (!runQueue.isEmpty()) {
            runQueue.remove().run();
        }
    }

    private Map<String, Module> parseToModules(final List<String> strings) {
        Map<String, Module> modules = new HashMap<>();
        Map<String, String[]> moduleConnections = new HashMap<>();
        for (String s : strings) {
            String[] nameAndConnections = s.replaceAll("\\s", "").split("->");
            String nameAndType = nameAndConnections[0];
            String[] connections = nameAndConnections[1].split(",");
            String name = nameAndType.replace(FLIP_FLOP, "").replace(CONJUNCTION, "");
            modules.computeIfAbsent(name, k -> Module.factory(nameAndType));
            moduleConnections.put(name, connections);
        }

        for (Map.Entry<String, String[]> entry : moduleConnections.entrySet()) {
            Module thisModule = modules.get(entry.getKey());
            for (String moduleName : entry.getValue()) {
                Module thatModule = modules.computeIfAbsent(moduleName, k -> Module.factory(moduleName));
                thisModule.addOutput(thatModule);
            }
        }
        Module button = new Button(BUTTON);
        modules.put(BUTTON, button);
        button.addOutput(modules.get(BROADCASTER));
        return modules;
    }

    private static final class PartOnePulseCounter implements Consumer<Pulse> {

        private int numLow = 0;
        private int numHigh = 0;

        @Override
        public void accept(final Pulse pulse) {
            if (Pulse.LOW.equals(pulse)) {
                numLow++;
            } else {
                numHigh++;
            }
        }
    }

    private static final class PartTwoLowPulseDetector implements Consumer<Pulse> {

        private boolean lowReceived = false;

        @Override
        public void accept(final Pulse pulse) {
            if (Pulse.LOW.equals(pulse)) {
                lowReceived = true;
            }
        }
    }

    @Getter
    private abstract static class Module {
        private final String name;
        private final List<Module> outputs = new ArrayList<>();
        private final List<Module> inputs = new ArrayList<>();
        private Consumer<Pulse> pulseSendConsumer = p -> {
        };
        private Consumer<Pulse> pulseReceiveConsumer = p -> {
        };

        private Module(final String name) {
            this.name = name;
        }

        public abstract void receive(Module sender, Pulse p, Queue<Runnable> runQueue);

        public void addOutput(final Module other) {
            outputs.add(other);
            other.inputs.add(this);
        }

        public void send(final Pulse p, final Queue<Runnable> runQueue) {
            for (Module m : outputs) {
                pulseSendConsumer.accept(p);
                runQueue.add(() -> m.receive(this, p, runQueue));
            }
        }

        public static Module factory(final String nameAndType) {
            if (nameAndType.contains(FLIP_FLOP)) {
                return new FlipFlop(nameAndType.replace(FLIP_FLOP, ""));
            }
            if (nameAndType.contains(CONJUNCTION)) {
                return new Conjunction(nameAndType.replace(CONJUNCTION, ""));
            }
            if (BROADCASTER.equals(nameAndType)) {
                return new Broadcast(nameAndType);
            }
            return new Output(nameAndType);
        }
    }

    private static final class Output extends Module {

        private Output(final String name) {
            super(name);
        }

        @Override
        public void receive(final Module sender, final Pulse p, final Queue<Runnable> runQueue) {
            getPulseReceiveConsumer().accept(p);
        }
    }

    private static final class Button extends Module {

        private Button(final String name) {
            super(name);
        }

        @Override
        public void receive(final Module sender, final Pulse p, final Queue<Runnable> runQueue) {
            send(Pulse.LOW, runQueue);
        }
    }

    private static final class Broadcast extends Module {

        private Broadcast(final String name) {
            super(name);
        }

        @Override
        public void receive(final Module sender, final Pulse p, final Queue<Runnable> runQueue) {
            send(p, runQueue);
        }
    }

    private static final class FlipFlop extends Module {
        private boolean on = false;

        private FlipFlop(final String name) {
            super(name);
        }

        @Override
        public void receive(final Module sender, final Pulse p, final Queue<Runnable> runQueue) {
            if (Pulse.HIGH.equals(p)) {
                return;
            }
            on = !on;
            if (on) {
                send(Pulse.HIGH, runQueue);
            } else {
                send(Pulse.LOW, runQueue);
            }
        }
    }

    private static final class Conjunction extends Module {
        private final Map<Module, Pulse> memory = new HashMap<>();

        private Conjunction(final String name) {
            super(name);
        }

        @Override
        public void receive(final Module sender, final Pulse p, final Queue<Runnable> runQueue) {
            updateMemory(sender, p);
            Pulse send = updateMemory(sender, p);
            send(send, runQueue);
        }

        public Pulse updateMemory(final Module sender, final Pulse p) {
            memory.put(sender, p);
            Pulse output = Pulse.LOW;
            for (Module input : getInputs()) {
                Pulse remembered = memory.computeIfAbsent(input, k -> Pulse.LOW);
                if (Pulse.LOW == remembered) {
                    output = Pulse.HIGH;
                }
            }
            return output;
        }
    }

    private enum Pulse {
        LOW,
        HIGH
    }
}
