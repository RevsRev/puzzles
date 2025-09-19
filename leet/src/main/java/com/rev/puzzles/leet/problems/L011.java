package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L011 {
    @LeetProblem(number = 11)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return maxArea((int[]) problemResourceLoader.resources()[0]);
    }

    public int maxArea(final int[] height) {
        int l = 0;
        int r = height.length - 1;
        int maxVolume = 0;
        int layer = 0;
        while (true) {
            while (height[l] < layer && l < r) {
                l++;
            }
            while (height[r] < layer && l < r) {
                r--;
            }
            final int vol = (r - l) * layer;
            if (vol > maxVolume) {
                maxVolume = vol;
            }
            if (r == l) {
                break;
            }
            layer++;
        }
        return maxVolume;
    }
}
