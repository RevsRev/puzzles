package com.rev.puzzles.leet.domain;

import java.util.Objects;

@SuppressWarnings("checkstyle:VisibilityModifier")
public final class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(final int val) {
        this.val = val;
    }

    public ListNode(final int val, final ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode fromArray(final int[] vals) {
        ListNode head = null;
        ListNode next = null;
        for (int i = vals.length - 1; i >= 0; i--) {
            next = head;
            head = new ListNode(vals[i]);
            head.next = next;
        }
        return head;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ListNode listNode = (ListNode) o;
        return val == listNode.val && Objects.equals(next, listNode.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, next);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    private void toString(final StringBuilder sb) {
        sb.append(val);
        if (next != null) {
            sb.append("->");
            next.toString(sb);
        }
    }
}
