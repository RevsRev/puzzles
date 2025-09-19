package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L042 {
    @LeetProblem(number = 42)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return trap((int[]) problemResourceLoader.resources()[0]);
    }

    public int trap(final int[] height) {
        int volume = 0;
        int i = 0;

        while (i < height.length) {
            int k = -1;
            int nextHighest = -1;
            for (int j = i + 1; j < height.length; j++) {
                if (height[j] > nextHighest) {
                    nextHighest = height[j];
                    k = j;
                }
                if (nextHighest >= height[i]) {
                    break;
                }
            }
            if (k >= 0) {
                volume += trapBetweenPeaks(height, i, k);
                i = k;
            } else {
                //nothing left to trap
                return volume;
            }
        }
        return volume;
    }

    private int trapBetweenPeaks(final int[] height, final int i, final int k) {
        int volume = 0;
        final int min = Math.min(height[i], height[k]);
        for (int j = i + 1; j < k; j++) {
            volume += min - height[j];
        }
        return volume;
    }


    // The initial approach below was too slow for all the test cases, and used too much memory
//    private static final int STATE_WALL = 1;
//    private static final int STATE_LEFT_UNDETERMINED = 2;
//    private static final int STATE_RIGHT_UNDETERMINED = 4;
//    private static final int STATE_LEFT_ENCLOSED = 8;
//    private static final int STATE_RIGHT_ENCLOSED = 16;
//
//    public int trap(int[] height) {
//        int count = 0;
//        int maxHeight = getMaxHeight(height);
//        for (int i=0; i<maxHeight; i++) {
//            int[] states = initialiseStates(height, i);
//            for (int j=0; j<height.length; j++) {
//                processRight(height, states, j);
//                processLeft(height, states, j);
//                if (((states[j] & STATE_LEFT_ENCLOSED) !=0) && ((states[j] & STATE_RIGHT_ENCLOSED) != 0)) {
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
//
//    private void processLeft(int[] height, int[] states, int j)
//    {
//        if (states[j] == STATE_WALL) {
//            return;
//        }
//
//        if ((states[j] & STATE_LEFT_UNDETERMINED) == 0) {
//            return;
//        }
//
//        if (j == 0) {
//            states[j] = states[j] & ~STATE_LEFT_UNDETERMINED;
//            return;
//        }
//
//        processLeft(height, states, j-1);
//        if ((states[j-1] & STATE_LEFT_ENCLOSED) == STATE_LEFT_ENCLOSED || states[j-1] == STATE_WALL) {
//            states[j] = (states[j] & ~STATE_LEFT_UNDETERMINED) | STATE_LEFT_ENCLOSED;
//        } else {
//            states[j] = states[j] & ~STATE_LEFT_UNDETERMINED;
//        }
//    }
//
//    private void processRight(int[] height, int[] states, int j)
//    {
//        if (states[j] == STATE_WALL) {
//            return;
//        }
//
//        if ((states[j] & STATE_RIGHT_UNDETERMINED) == 0) {
//            return;
//        }
//
//        if (j == height.length - 1) {
//            states[j] = states[j] & ~STATE_RIGHT_UNDETERMINED;
//            return;
//        }
//
//        processRight(height, states, j+1);
//        if ((states[j+1] & STATE_RIGHT_ENCLOSED) == STATE_RIGHT_ENCLOSED || states[j+1] == STATE_WALL) {
//            states[j] = (states[j] & ~STATE_RIGHT_UNDETERMINED) | STATE_RIGHT_ENCLOSED;
//        } else {
//            states[j] = states[j] & ~STATE_RIGHT_UNDETERMINED;
//        }
//    }
//
//
//    private int[] initialiseStates(int[] height, int i) {
//        int width = height.length;
//        int[] states = new int[width];
//
//        for (int j=0; j<height.length; j++) {
//            if (height[j] > i) {
//                states[j] = STATE_WALL;
//            } else {
//                states[j] = STATE_LEFT_UNDETERMINED | STATE_RIGHT_UNDETERMINED;
//            }
//        }
//        return states;
//    }
//
//    private int getMaxHeight(int[] height)
//    {
//        int maxHeight = height[0];
//        for (int i=1; i<height.length; i++) {
//            if (height[i]> maxHeight) {
//                maxHeight = height[i];
//            }
//        }
//        return maxHeight;
//    }
}
