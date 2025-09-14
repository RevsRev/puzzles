package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class D01 extends AocProblem<Long, Long> {

    @AocProblemI(year = 2024, day = 1, part = 1)
    @Override
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        loadLists(resourceLoader, leftList, rightList);

        long totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return totalDistance;
    }

    @AocProblemI(year = 2024, day = 1, part = 2)
    @Override
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        loadLists(resourceLoader, leftList, rightList);

        Set<Integer> leftNums = new HashSet<>(leftList);
        Map<Integer, Integer> rightNumsAndCounts = new HashMap<>();

        for (int i = 0; i < rightList.size(); i++) {
            Integer num = rightList.get(i);
            int count = rightNumsAndCounts.computeIfAbsent(num, k -> 0);
            rightNumsAndCounts.put(num, count + 1);
        }

        long score = 0;
        for (int i = 0; i < leftList.size(); i++) {
            Integer num = leftList.get(i);
            if (rightNumsAndCounts.containsKey(num)) {
                score += rightNumsAndCounts.get(num) * num;
            }
        }
        return score;
    }

    private void loadLists(
            final ResourceLoader resourceLoader,
            final List<Integer> leftList,
            final List<Integer> rightList) {
        List<String> listsLines = resourceLoader.resources();
        for (String line : listsLines) {
            String[] split = line.split("\\s+");
            leftList.add(Integer.parseInt(split[0]));
            rightList.add(Integer.parseInt(split[1]));
        }
        Collections.sort(leftList);
        Collections.sort(rightList);
    }
}
