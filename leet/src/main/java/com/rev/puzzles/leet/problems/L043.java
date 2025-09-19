package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashMap;
import java.util.Map;

public final class L043 {
    private static final Map<Character, Character> CHARS_TO_INTS = charsToInts();
    private static final Map<Character, Character> INTS_TO_CHARS = intsToChars();

    private static Map<Character, Character> charsToInts() {
        final Map<Character, Character> map = new HashMap<>();
        map.put('0', (char) 0);
        map.put('1', (char) 1);
        map.put('2', (char) 2);
        map.put('3', (char) 3);
        map.put('4', (char) 4);
        map.put('5', (char) 5);
        map.put('6', (char) 6);
        map.put('7', (char) 7);
        map.put('8', (char) 8);
        map.put('9', (char) 9);
        return map;
    }

    private static Map<Character, Character> intsToChars() {
        final Map<Character, Character> map = new HashMap<>();
        map.put((char) 0, '0');
        map.put((char) 1, '1');
        map.put((char) 2, '2');
        map.put((char) 3, '3');
        map.put((char) 4, '4');
        map.put((char) 5, '5');
        map.put((char) 6, '6');
        map.put((char) 7, '7');
        map.put((char) 8, '8');
        map.put((char) 9, '9');
        return map;
    }

    @LeetProblem(number = 43)
    public String apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return multiply((String) problemResourceLoader.resources()[0], (String) problemResourceLoader.resources()[1]);
    }

    public String multiply(final String num1, final String num2) {
        if (num1.charAt(0) == '0' || num2.charAt(0) == '0') {
            return "0";
        }

        final char[] n1 = asCharArray(num1);
        final char[] n2 = asCharArray(num2);
        final char[] result = new char[n1.length + n2.length];

        for (int i = 0; i < n2.length; i++) {
            final char[] toAdd = multiply(n1, n2[i], i);
            add(result, toAdd);
        }

        return asString(result);
    }

    private char[] multiply(final char[] n1, final char val, final int offset) {
        final char[] result = new char[n1.length + offset + 1];
        char carry = 0;
        for (int i = 0; i < n1.length; i++) {
            final char mult = (char) (n1[i] * val + carry);
            result[i + offset] = (char) (mult % 10);
            carry = (char) (mult / 10);
        }
        if (carry != 0) {
            result[n1.length + offset] = carry;
        }
        return result;
    }

    private void add(final char[] result, final char[] toAdd) {
        char carry = 0;
        for (int i = 0; i < toAdd.length; i++) {
            final char sum = (char) (toAdd[i] + result[i] + carry);
            result[i] = (char) (sum % 10);
            carry = (char) (sum / 10);
        }
        if (carry != 0) {
            result[toAdd.length + 1] = carry;
        }
    }

    private String asString(final char[] num) {
        final StringBuilder sb = new StringBuilder();
        int end = num.length - 1;
        while (num[end] == 0) {
            end--;
        }
        for (int i = end; i >= 0; i--) {
            sb.append(INTS_TO_CHARS.get(num[i]));
        }
        return sb.toString();
    }

    private char[] asCharArray(final String num) {
        final char[] result = new char[num.length()];
        for (int i = 0; i < num.length(); i++) {
            result[i] = CHARS_TO_INTS.get(num.charAt(num.length() - 1 - i));
        }
        return result;
    }

}
