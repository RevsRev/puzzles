package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("checkstyle:VisibilityModifier")
public final class L010 {
    private final State acceptingState = new State(null, (char) 0, null);

    @LeetProblem(number = 10)
    public Boolean apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return isMatch((String) problemResourceLoader.resources()[0], (String) problemResourceLoader.resources()[1]);
    }

    public boolean isMatch(final String s, final String p) {
        final State first = compile(p);
        Set<State> states = new HashSet<>();
        states.add(first);

        final char[] sChars = s.toCharArray();
        for (int i = 0; i < sChars.length; i++) {
            final char c = sChars[i];
            final Set<State> nextStates = new HashSet<>();
            final Iterator<State> statesIt = states.iterator();
            while (statesIt.hasNext()) {
                final State state = statesIt.next();
                nextStates.addAll(state.delta(c));
            }
            states = nextStates;
        }

        return states.contains(acceptingState);
    }

    public State compile(final String p) {
        final char[] charArr = p.toCharArray();
        State previous = acceptingState;
        for (int i = charArr.length - 1; i >= 0; i--) {
            char c = charArr[i];
            final boolean isWildcard = c == '*';
            if (isWildcard) {
                i--;
                c = charArr[i];
                previous = new State(previous, c, null);
                previous.nextState = previous;
            } else {
                previous = new State(null, c, previous);
            }
        }
        return previous;
    }

    public static final class State {

        State epsilon = null;
        char matchingChar = 0;
        State nextState = null;

        public State(final State epsilon, final char matchingChar, final State nextState) {
            this.epsilon = epsilon;
            this.matchingChar = matchingChar;
            this.nextState = nextState;
        }

        public Set<State> delta(final Character c) {
            final HashSet<State> retval = new HashSet<>();
            if ((matchingChar == '.' || matchingChar == c) && nextState != null) {
                retval.add(nextState);
                retval.addAll(nextState.resolveEpsilons());
            }
            if (epsilon != null) {
                retval.addAll(epsilon.delta(c));
            }
            return retval;
        }

        public Set<State> resolveEpsilons() {
            State eps = epsilon;
            final Set<State> retval = new HashSet<>();
            while (eps != null) {
                retval.add(eps);
                eps = eps.epsilon;
            }
            return retval;
        }
    }


}
