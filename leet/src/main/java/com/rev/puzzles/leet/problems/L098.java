package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.TreeNode;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L098 {
    @LeetProblem(number = 98)
    public Boolean apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return isValidBST((TreeNode) problemResourceLoader.resources()[0]);
    }

    public boolean isValidBST(final TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(final TreeNode root, final long low, final long high) {
        if (root == null) {
            return true;
        }

        if (root.left != null && (root.left.val <= low || root.left.val >= root.val)) {
            return false;
        }
        if (root.right != null && (root.right.val >= high || root.right.val <= root.val)) {
            return false;
        }

        return isValidBST(root.left, low, root.val) && isValidBST(root.right, root.val, high);
    }
}
