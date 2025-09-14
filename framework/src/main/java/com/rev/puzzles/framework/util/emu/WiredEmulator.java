package com.rev.puzzles.framework.util.emu;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public final class WiredEmulator<T> {

    private static final Pattern NUMBERS_PATTERN = Pattern.compile("^[0-9]*$");

    private final Map<String, SignalInput<T>> wiring;

    private WiredEmulator(final Map<String, SignalInput<T>> wiring) {
        this.wiring = wiring;
    }

    public T getSignal(final String name) {
        return wiring.get(name).compute();
    }

    public void setSignal(final String name, final T value) {
        wiring.get(name).set(value);
    }

    public void clearSignals() {
        wiring.values().forEach(SignalInput::reset);
    }

    public static WiredEmulator<Long> create(final List<String> textWiring) {
        final Map<String, String> symbolMap = new HashMap<>();
        for (String line : textWiring) {
            final String s = line.replaceAll("\\s", "");
            final String[] split = s.split("->");
            final String inputs = split[0];
            final String name = split[1];
            symbolMap.put(name, inputs);
        }

        final Map<String, SignalInput<Long>> signalsMap = new HashMap<>();
        for (String name : symbolMap.keySet()) {
            extractSignals(name, symbolMap, signalsMap);
        }
        return new WiredEmulator<>(signalsMap);
    }

    private static void extractSignals(final String name,
                                       final Map<String, String> symbolMap,
                                       final Map<String, SignalInput<Long>> signalsMap) {
        if (signalsMap.containsKey(name)) {
            return;
        }
        final String wires = symbolMap.get(name);
        final Pair<List<String>, Function<List<Long>, Long>> symbolsAndOperator = extractSymbolsAndOperator(wires);
        final List<String> dependencies = symbolsAndOperator.getLeft();
        for (String dependency : dependencies) {
            extractSignals(dependency, symbolMap, signalsMap);
        }

        final List<SignalInput<Long>> signalInputs = new ArrayList<>();
        dependencies.forEach(dep -> signalInputs.add(signalsMap.get(dep)));
        signalsMap.put(name, new SignalTransmitter<>(signalInputs, symbolsAndOperator.getRight()));
    }

    private static Pair<List<String>, Function<List<Long>, Long>> extractSymbolsAndOperator(final String wires) {
        if (NUMBERS_PATTERN.matcher(wires).matches()) {
            long input = Long.parseLong(wires);
            return Pair.of(List.of(), list -> input);
        }

        if (wires.contains("LSHIFT")) {
            final String[] split = wires.split("LSHIFT");
            final String symbol = split[0];
            final long shift = Long.parseLong(split[1]);
            return Pair.of(List.of(symbol), list -> list.get(0) << shift);
        }

        if (wires.contains("RSHIFT")) {
            final String[] split = wires.split("RSHIFT");
            final String symbol = split[0];
            final long shift = Long.parseLong(split[1]);
            return Pair.of(List.of(symbol), list -> list.get(0) >> shift);
        }

        if (wires.contains("AND")) {
            final String[] split = wires.split("AND");
            final String firstSymbolOrNumeric = split[0];
            final String secondSymbolOrNumeric = split[1];

            if (NUMBERS_PATTERN.matcher(firstSymbolOrNumeric).matches()) {
                final long first = Long.parseLong(firstSymbolOrNumeric);
                if (NUMBERS_PATTERN.matcher(secondSymbolOrNumeric).matches()) {
                    final long second = Long.parseLong(secondSymbolOrNumeric);
                    return Pair.of(List.of(), list -> first & second);
                }
                return Pair.of(List.of(secondSymbolOrNumeric), list -> first & list.get(0));
            }

            if (NUMBERS_PATTERN.matcher(secondSymbolOrNumeric).matches()) {
                final long second = Long.parseLong(secondSymbolOrNumeric);
                return Pair.of(List.of(firstSymbolOrNumeric), list -> list.get(0) & second);
            }

            return Pair.of(List.of(split[0], split[1]), list -> (list.get(0) & list.get(1)));
        }

        if (wires.contains("OR")) {
            final String[] split = wires.split("OR");
            final String firstSymbolOrNumeric = split[0];
            final String secondSymbolOrNumeric = split[1];

            if (NUMBERS_PATTERN.matcher(firstSymbolOrNumeric).matches()) {
                final long first = Long.parseLong(firstSymbolOrNumeric);
                if (NUMBERS_PATTERN.matcher(secondSymbolOrNumeric).matches()) {
                    final long second = Long.parseLong(secondSymbolOrNumeric);
                    return Pair.of(List.of(), list -> first | second);
                }
                return Pair.of(List.of(secondSymbolOrNumeric), list -> first | list.get(0));
            }

            if (NUMBERS_PATTERN.matcher(secondSymbolOrNumeric).matches()) {
                final long second = Long.parseLong(secondSymbolOrNumeric);
                return Pair.of(List.of(firstSymbolOrNumeric), list -> list.get(0) | second);
            }

            return Pair.of(List.of(split[0], split[1]), list -> (list.get(0) | list.get(1)));
        }

        if (wires.contains("NOT")) {
            final String symbol = wires.replace("NOT", "");
            return Pair.of(List.of(symbol), list -> ~list.get(0));
        }

        //Assume that the input wires is just a reference to another node.
        return Pair.of(List.of(wires), list -> list.get(0));
    }
}
