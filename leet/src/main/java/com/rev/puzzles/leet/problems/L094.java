package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.TreeNode;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.List;

public final class L094 {
    @LeetProblem(number = 94)
    public List<Integer> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return inorderTraversal((TreeNode) problemResourceLoader.resources()[0]);
    }

    public List<Integer> inorderTraversal(final TreeNode root) {
        final List<Integer> inOrder = new ArrayList<>();
        inorderTraversal(root, inOrder);
        return inOrder;
    }

    public void inorderTraversal(final TreeNode node, final List<Integer> inOrder) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, inOrder);
        inOrder.add(node.val);
        inorderTraversal(node.right, inOrder);
    }
}
