package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.ListNode;
import com.rev.puzzles.leet.framework.LeetProblem;

@SuppressWarnings("checkstyle:FinalParameters")
public final class L021 {

    @LeetProblem(number = 21)
    public ListNode apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return mergeTwoLists((ListNode) problemResourceLoader.resources()[0],
                (ListNode) problemResourceLoader.resources()[1]);
    }

    public ListNode mergeTwoLists(final ListNode list1, final ListNode list2) {

        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode smaller = list1;
        ListNode larger = list2;
        if (list1.val > list2.val) {
            smaller = list2;
            larger = list1;
        }

        final ListNode head = smaller;

        mergeSmallerLarger(smaller, larger);
        return head;
    }

    private void mergeSmallerLarger(ListNode smaller, final ListNode larger) {
        if (larger == null) {
            return;
        }

        while (smaller.next != null && smaller.next.val < larger.val) {
            smaller = smaller.next;
        }
        final ListNode dangling = smaller.next;
        smaller.next = larger;
        mergeSmallerLarger(larger, dangling);
    }
}
