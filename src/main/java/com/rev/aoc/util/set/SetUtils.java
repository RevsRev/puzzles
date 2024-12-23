package com.rev.aoc.util.set;

import java.util.HashSet;
import java.util.Set;

public final class SetUtils {

    private SetUtils() {
    }

    public static <T> Set<T> copyRemoveAll(final Set<T> set, final Set<T> remove) {
        Set<T> copy = new HashSet<>(set);
        copy.removeAll(remove);
        return copy;
    }
    public static <T> Set<T> copyRetainAll(final Set<T> set, final Set<T> retain) {
        Set<T> copy = new HashSet<>(set);
        copy.retainAll(retain);
        return copy;
    }
}
