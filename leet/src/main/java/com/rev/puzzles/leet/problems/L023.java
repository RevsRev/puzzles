package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.ListNode;
import com.rev.puzzles.leet.framework.LeetProblem;

@SuppressWarnings("checkstyle:FinalParameters")
public final class L023 {
    @LeetProblem(number = 23)
    public ListNode apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return mergeKLists((ListNode[]) problemResourceLoader.resources()[0]);
    }

    //Alternate solution - use a heap and extract nodes from the heap

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        while (lists.length > 1) {
            final int newSize = lists.length % 2 == 0 ? lists.length / 2 : lists.length / 2 + 1;
            final ListNode[] newLists = new ListNode[newSize];
            for (int i = 0; i < lists.length; i++) {
                if (i % 2 == 0 && i < lists.length - 1) {
                    newLists[i / 2] = mergeTwoLists(lists[i], lists[i + 1]);
                } else if (i % 2 == 0 && i == lists.length - 1) {
                    newLists[newSize - 1] = lists[i];
                }
            }
            lists = newLists;
        }
        return lists[0];
    }


    private ListNode mergeTwoLists(final ListNode list1, final ListNode list2) {

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
