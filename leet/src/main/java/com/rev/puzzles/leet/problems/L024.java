package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.ListNode;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L024 {
    @LeetProblem(number = 24)
    public ListNode apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return swapPairs((ListNode) problemResourceLoader.resources()[0]);
    }

    public ListNode swapPairs(final ListNode head) {
        if (head == null) {
            return null;
        }

        final ListNode second = head.next;

        ListNode firstInPair = head;
        ListNode previous = null;

        while (firstInPair != null) {
            final ListNode secondInPair = firstInPair.next;

            //link the second thing to the first and update our variables
            if (secondInPair != null) {
                //cache the start of the next pair
                final ListNode nextFirstInPair = secondInPair.next;

                if (previous != null) {
                    previous.next = secondInPair;
                }

                //link the second to the first
                secondInPair.next = firstInPair;

                //Unlink. This will be linked in the next iteration, or if there is no next iteration then it is the
                // end of the list.
                firstInPair.next = null;

                //update our previous and the start of the next pair to consider
                previous = firstInPair;
                firstInPair = nextFirstInPair;
            } else {
                if (previous != null) {
                    previous.next = firstInPair;
                }
                break;
            }
        }

        return second != null ? second : head;
    }
}
