package com.rev.puzzles.math.perm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class PalindromeTest {

    @Test
    public void testArrayIsPalindrome() {
        final Map[] arr = new Map[3];
        arr[0] = Map.of("Hello", "World", "Foo", "Bar");
        arr[1] = Map.of("Middle doesn't", "really matter");
        arr[2] = Map.of("Hello", "World", "Foo", "Bar");
        Assertions.assertTrue(Palindrome.isPalindrome(arr));
    }

    @Test
    public void testArrayIsNotPalindrome() {
        final String[] notPalindrome =
                new String[]{"this", "is", "nearly", "a", "palindrome", "a", "nearly", "i5", "this",};
        Assertions.assertFalse(Palindrome.isPalindrome(notPalindrome));
    }

    @Test
    public void testLongIsPalindrome() {
        Assertions.assertTrue(Palindrome.isPalindromeBase10(123321));
        Assertions.assertTrue(Palindrome.isPalindromeBase10(1234321));
    }

    @Test
    public void testLongIsNotPalindrome() {
        Assertions.assertFalse(Palindrome.isPalindromeBase10(123322));
        Assertions.assertFalse(Palindrome.isPalindromeBase10(1234322));
    }

}