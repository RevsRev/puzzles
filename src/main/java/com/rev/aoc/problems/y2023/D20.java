package com.rev.aoc.problems.y2023;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public final class D20 extends AocProblem {

    public static final String FLIP_FLOP = "%";
    public static final String CONJUNCTION = "&";
    public static final String BROADCASTER = "broadcaster";
    public static final String BUTTON = "button";

    private static final int NUM_BUTTON_PRESSES = 1000;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2023, 20);
    }

    @Override
    protected long partOneImpl() {
        List<String> strings = loadResources();
        Map<String, Module> modules = parseToModules(strings);

        PartOnePulseCounter pulseCounter = new PartOnePulseCounter();
        for (Module m : modules.values()) {
            m.pulseConsumer = pulseCounter;
        }
        Module button = modules.get(BUTTON);
        for (int i = 0; i < NUM_BUTTON_PRESSES; i++) {
            start(button);
        }
        return pulseCounter.numHigh * pulseCounter.numLow;
    }

    @Override
    protected long partTwoImpl() {
        return 0;
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
            String[] nameAndConnections = s.replaceAll("\s", "").split("->");
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

    @Getter
    private abstract static class Module {
        private final String name;
        private final List<Module> outputs = new ArrayList<>();
        private final List<Module> inputs = new ArrayList<>();
        private Consumer<Pulse> pulseConsumer = p -> {
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
            for (Module m: outputs) {
                pulseConsumer.accept(p);
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
            return new Broadcast(nameAndType);
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
