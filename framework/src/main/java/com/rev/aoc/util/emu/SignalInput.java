package com.rev.aoc.util.emu;

interface SignalInput<T> {
    T compute();

    void reset();

    void set(T value);
}
