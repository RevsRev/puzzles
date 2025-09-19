package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L004 {
    @LeetProblem(number = 4)
    public Double apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return findMedianSortedArrays((int[]) problemResourceLoader.resources()[0],
                (int[]) problemResourceLoader.resources()[1]);
    }

    public double findMedianSortedArrays(final int[] nums1, final int[] nums2) {

        if (nums1 == null || nums1.length == 0) {
            return medianSortedArray(nums2);
        }
        if (nums2 == null || nums2.length == 0) {
            return medianSortedArray(nums1);
        }

        final int[] shortest = nums1.length < nums2.length ? nums1 : nums2;
        final int[] longest = nums1.length < nums2.length ? nums2 : nums1;

        // Define L to be the position of the median element (if sum is odd), or the position of the first median
        // element (if sum is even)
        // Let r & s be such that the first r elements of shortest and s elements of longest are the first r+s
        // elements of the sorted combined list
        // Then the median will be max(shortest[r], longest[s]) in the odd case
        // A bit more complicated in the even case

        //Initially, we'll guess r as the largest power of two less than or equal to the length of shortest
        int pow = 1;
        while (pow * 2 < shortest.length) {
            pow *= 2;
        }

        final int l = (shortest.length + longest.length + 1) / 2;
        int r = pow;
        int s = l - pow;

        while (pow > 0) {
            pow /= 2;
            while (r >= shortest.length && pow > 0) {
                r -= pow;
                s += pow;
                pow /= 2;
            }

            if (r < shortest.length && shortest[r] < longest[s - 1]) {
                r += pow;
                s -= +pow;
            } else if (r > 1 && shortest[r - 1] > longest[s]) {
                r -= pow;
                s += pow;
            } else {
                break;
            }
        }

        //Need to do one final check
        if (r < shortest.length && shortest[r] < longest[s - 1]) {
            r += 1;
            s -= 1;
        } else if (r > 0 && shortest[r - 1] > longest[s]) {
            r -= 1;
            s += 1;
        }

        if ((longest.length + shortest.length) % 2 == 1) {
            final int medianElement;
            if (r == 0) {
                medianElement = longest[s - 1];
            } else if (s == 0) {
                medianElement = shortest[r - 1];
            } else {
                medianElement = Math.max(longest[s - 1], shortest[r - 1]);
            }
            return medianElement;
        }

        final int firstMedianElement;
        final int secondMedianElement;
        if (r == 0) {
            firstMedianElement = longest[s - 1];
            if (s < longest.length) {
                secondMedianElement = Math.min(shortest[0], longest[s]);
            } else {
                secondMedianElement = shortest[0];
            }
            return (firstMedianElement + secondMedianElement) / 2.0;
        } else if (s == 0) {
            firstMedianElement = shortest[r - 1];
            if (r < shortest.length) {
                secondMedianElement = Math.min(longest[0], shortest[r]);
            } else {
                secondMedianElement = longest[0];
            }
            return (firstMedianElement + secondMedianElement) / 2.0;
        }

        final boolean rBiggerThanS = shortest[r - 1] > longest[s - 1];
        if (rBiggerThanS) {
            firstMedianElement = shortest[r - 1];

            if (r < shortest.length && s < longest.length) {
                secondMedianElement = Math.min(shortest[r], longest[s]);
            } else if (r < shortest.length) {
                secondMedianElement = shortest[r];
            } else {
                secondMedianElement = longest[s];
            }
        } else {
            firstMedianElement = longest[s - 1];
            if (r < shortest.length && s < longest.length) {
                secondMedianElement = Math.min(shortest[r], longest[s]);
            } else if (s < longest.length) {
                secondMedianElement = longest[s];
            } else {
                secondMedianElement = shortest[r];
            }
        }
        return (firstMedianElement + secondMedianElement) / 2.0;
    }

    public double medianSortedArray(final int[] nums) {
        final boolean even = nums.length % 2 == 0;
        final int mid = nums.length / 2;
        if (even) {
            return (nums[mid - 1] + nums[mid]) / 2.0;
        }
        return nums[mid];
    }
}
