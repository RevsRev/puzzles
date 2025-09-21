package com.rev.puzzles.math.perm;

public final class Palindrome {

    private Palindrome() {
    }

    public static boolean isPalindromeBase10(long n) {
        final int len = (int) Math.log10(n) + 1;
        final Long[] arr = new Long[len];
        int i = 0;
        while (n > 0) {
            arr[i] = n % 10;
            n = n / 10;
            i++;
        }
        return isPalindrome(arr);
    }

    public static <T> boolean isPalindrome(final T[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            if (!arr[start].equals(arr[end])) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
}
