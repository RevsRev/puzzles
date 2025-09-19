package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.ListNode;
import com.rev.puzzles.leet.framework.LeetProblem;

@SuppressWarnings("checkstyle:FinalParameters")
public final class L002 {
    @LeetProblem(number = 2)
    public ListNode apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return addTwoNumbers((ListNode) problemResourceLoader.resources()[0],
                (ListNode) problemResourceLoader.resources()[1]);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        final ListNode retval = new ListNode();
        ListNode child = retval;
        int carry = 0;
        while (l1 != null || l2 != null) {
            final int l1Val = l1 != null ? l1.val : 0;
            final int l2Val = l2 != null ? l2.val : 0;
            final int sum = l1Val + l2Val + carry;
            carry = sum / 10;
            child.val = sum % 10;

            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;

            if (l1 != null || l2 != null) {
                child.next = new ListNode();
                child = child.next;
            }

        }

        if (carry == 1) {
            child.next = new ListNode();
            child.next.val = 1;
        }

        return retval;
    }
}
