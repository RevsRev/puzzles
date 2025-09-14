package com.rev.puzzles.framework.util.emu;

interface SignalInput<T> {
    T compute();

    void reset();

    void set(T value);
}
