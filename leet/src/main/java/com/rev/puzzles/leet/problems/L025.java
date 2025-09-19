package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.ListNode;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L025 {
    @LeetProblem(number = 25)
    public ListNode apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return reverseKGroup((ListNode) problemResourceLoader.resources()[0],
                (Integer) problemResourceLoader.resources()[1]);
    }

    public ListNode reverseKGroup(final ListNode head, final int k) {
        ListNode node = head;

        int count = 1;
        while (node != null && count < k) {
            node = node.next;
            count += 1;
        }

        if (node == null) {
            return head;
        }

        final ListNode end = node.next;

        node = head;
        ListNode next = node.next;
        for (int i = 1; i < k; i++) {
            final ListNode cachedNext = next.next;
            next.next = node;
            node = next;
            next = cachedNext;
        }
        head.next = reverseKGroup(end, k);
        return node;
    }
}
