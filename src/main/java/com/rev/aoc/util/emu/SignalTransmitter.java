package com.rev.aoc.util.emu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class SignalTransmitter<T> implements SignalInput<T> {
    private final List<SignalInput<T>> signalInputs;
    private final Function<List<T>, T> resolver;
    private T cachedResult = null;

    SignalTransmitter(List<SignalInput<T>> signalInputs, Function<List<T>, T> resolver) {
        this.signalInputs = signalInputs;
        this.resolver = resolver;
    }

    @Override
    public T compute() {
        if (cachedResult != null) {
            return cachedResult;
        }
        List<T> inputs = new ArrayList<>(signalInputs.size());
        for (SignalInput<T> signalInput : signalInputs) {
            inputs.add(signalInput.compute());
        }
        cachedResult = resolver.apply(inputs);
        return cachedResult;
    }

    @Override
    public void reset() {
        cachedResult = null;
    }

    @Override
    public void set(T value) {
        cachedResult = value;
    }
}
