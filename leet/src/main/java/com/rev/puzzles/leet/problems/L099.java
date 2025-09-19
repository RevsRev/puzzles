package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.domain.TreeNode;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SuppressWarnings("checkstyle:FinalParameters")
public final class L099 {
    @LeetProblem(number = 99)
    public Object[] apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
//        recoverTree((TreeNode)problemResourceLoader.resources()[0]);
        return problemResourceLoader.resources();
    }

    public void recoverTree(final TreeNode root) {
        final List<TreeNode> postOrderedNodes = getPostOrderedNodes(root);
        TreeNode first = null;
        TreeNode second = null;
        for (int i = 0; i < postOrderedNodes.size(); i++) {
            if (i < postOrderedNodes.size() - 1 && postOrderedNodes.get(i).val > postOrderedNodes.get(i + 1).val) {
                first = postOrderedNodes.get(i);
            }
            if (i > 0 && postOrderedNodes.get(i - 1).val < postOrderedNodes.get(i).val) {
                second = postOrderedNodes.get(i);
            }
        }
        final int firstVal = first.val;
        first.val = second.val;
        second.val = firstVal;
    }

    private List<TreeNode> getPostOrderedNodes(TreeNode root) {
        final Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        final List<TreeNode> orderedNodes = new ArrayList<>();
        while (!stack.isEmpty()) {
            while (root.left != null) {
                stack.push(root.left);
                root = root.left;
            }

            root = stack.pop();
            orderedNodes.add(root);
            if (root.right != null) {
                root = root.right;
                stack.push(root);
            }
        }
        return orderedNodes;
    }
}
