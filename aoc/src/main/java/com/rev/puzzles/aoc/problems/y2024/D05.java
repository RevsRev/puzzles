package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ResourceLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class D05 {

    private final Map<Pair<Integer, Integer>, Boolean> ordinals = new HashMap<>();
    private final List<Integer[]> updates = new ArrayList<>();

    @AocProblemI(year = 2024, day = 5, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        loadMapsAndUpdates(resourceLoader);
        List<Integer[]> orderedUpdates = new ArrayList<>();
        List<Integer[]> unorderedUpdates = new ArrayList<>();
        checkUpdates(orderedUpdates, unorderedUpdates);
        long result = 0;
        for (Integer[] update : orderedUpdates) {
            result += update[update.length / 2];
        }
        return result;
    }

    @AocProblemI(year = 2024, day = 5, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        loadMapsAndUpdates(resourceLoader);
        List<Integer[]> orderedUpdates = new ArrayList<>();
        List<Integer[]> unorderedUpdates = new ArrayList<>();
        checkUpdates(orderedUpdates, unorderedUpdates);
        List<Integer[]> fixedUpdates = fixUnorderedUpdates(unorderedUpdates);
        long result = 0;
        for (Integer[] update : fixedUpdates) {
            result += update[update.length / 2];
        }
        return result;
    }

    private List<Integer[]> fixUnorderedUpdates(final List<Integer[]> unorderedUpdates) {
        List<Integer[]> fixed = new ArrayList<>();
        for (Integer[] broken : unorderedUpdates) {
            fixed.add(fixUnorderedUpdate(broken));
        }
        return fixed;
    }

    private Integer[] fixUnorderedUpdate(final Integer[] broken) {
        List<Integer> fixed = new ArrayList<>(broken.length);
        for (int i = 0; i < broken.length; i++) {
            int insertIndex = 0;
            for (int j = 0; j < fixed.size() && !ordinals.get(Pair.of(broken[i], fixed.get(j))); j++) {
                insertIndex++;
            }
            fixed.add(insertIndex, broken[i]);
        }
        return fixed.toArray(new Integer[0]);
    }

    private void checkUpdates(final List<Integer[]> orderedUpdates, final List<Integer[]> unorderedUpdates) {
        for (Integer[] update : updates) {
            if (checkUpdateIsOrdered(update)) {
                orderedUpdates.add(update);
            } else {
                unorderedUpdates.add(update);
            }
        }
    }

    private boolean checkUpdateIsOrdered(final Integer[] update) {
        for (int i = 0; i < update.length; i++) {
            for (int j = i + 1; j < update.length; j++) {
                if (!ordinals.get(Pair.of(update[i], update[j]))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void loadMapsAndUpdates(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();

        ordinals.clear();
        updates.clear();

        int i = 0;
        while (i < lines.size() && lines.get(i).contains("|")) {
            String[] toks = lines.get(i).trim().split("\\|");
            int key = Integer.parseInt(toks[0]);
            int value = Integer.parseInt(toks[1]);
            ordinals.put(Pair.of(key, value), true);
            ordinals.put(Pair.of(value, key), false);
            i++;
        }

        while (lines.get(i).isEmpty()) {
            i++;
        }

        while (i < lines.size()) {
            String[] updateToks = lines.get(i).trim().split(",");
            Integer[] update = new Integer[updateToks.length];
            for (int j = 0; j < updateToks.length; j++) {
                update[j] = Integer.parseInt(updateToks[j]);
            }
            updates.add(update);
            i++;
        }
    }
}
