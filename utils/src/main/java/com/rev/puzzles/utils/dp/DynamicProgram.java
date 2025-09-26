package com.rev.puzzles.utils.dp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class DynamicProgram<K, V> {

    private final Map<K, V> computed = new HashMap<>();
    private final BiFunction<DynamicProgram<K, V>, K, V> function;

    public DynamicProgram(final BiFunction<DynamicProgram<K, V>, K, V> function) {
        this.function = function;
    }

    public V compute(final K input) {
        final V value = computed.get(input);
        if (value != null) {
            return value;
        }
        final V apply = function.apply(this, input);
        computed.put(input, apply);
        return apply;
    }
}
