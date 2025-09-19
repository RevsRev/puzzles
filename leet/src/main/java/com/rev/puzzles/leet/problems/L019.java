package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.ListNode;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("checkstyle:FinalParameters")
public final class L019 {
    @LeetProblem(number = 19)
    public ListNode apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return removeNthFromEnd((ListNode) problemResourceLoader.resources()[0],
                (Integer) problemResourceLoader.resources()[1]);
    }

    public ListNode removeNthFromEnd(ListNode head, final int n) {
        ListNode node = head;
        final Map<Integer, ListNode> nodeIndices = new HashMap();
        int index = 0;
        while (node != null) {
            nodeIndices.put(index, node);
            node = node.next;
            index++;
        }

        final ListNode left = nodeIndices.get(index - n - 1);
        //ListNode middle = nodeIndices.get(index-n); //the one to remove
        final ListNode right = nodeIndices.get(index - n + 1);

        if (left != null) {
            left.next = right;
        } else {
            head = right;
        }
        return head;
    }

}
