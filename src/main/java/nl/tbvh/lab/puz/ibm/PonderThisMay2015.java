package nl.tbvh.lab.puz.ibm;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.research.ibm.com/haifa/ponderthis/challenges/May2015.html
 */
public class PonderThisMay2015 {
    int maxInit = 255;
    int max = maxInit * 3;
    int maxIndex = max + 1;
    int[][][] states = new int[maxIndex][maxIndex][maxIndex];
    List<State> nextRound = new ArrayList<>();

    public static void main(String[] args) {
        long millis = System.currentTimeMillis();
        new PonderThisMay2015()
            .solve();
        long elapsed = System.currentTimeMillis() - millis;
        System.out.println("Took " + elapsed + " millis");
    }

    private void solve() {
        initializeEndstates();
        for (int i = 0; i < 15; i++) {
            expandStates(i + 1);
            System.out.println("Turn = " + i + ", " + nextRound.size()); // TODO
        }

        for (int i = 1; i <= maxInit; i++) {
            for (int j = i; j <= maxInit; j++) {
                for (int k = j; k <= maxInit; k++) {
                    int val = fetch(i, j, k);
                    if (val >= 11) {
                        System.out.println(pos(i, j, k) + " " + fetch(i, j, k));
                    }
                }
            }
        }
    }

    private String pos(int i, int j, int k) {
        return i + ", " + j + ", " + k;
    }

    private void expandStates(int turn) {
        List<State> thisRound = nextRound;
        nextRound = new ArrayList<>();
        for (State state : thisRound) {
            expand(state, turn);
        }
    }

    private void expand(State state, int turn) {
        move(state.i, state.j, state.k, turn);
        move(state.j, state.i, state.k, turn);
        move(state.k, state.i, state.j, turn);
    }

    // 1, 4, 6
    // -> 2,3,6 (1 wins against 4)
    // -> 1,8,2 (4 wins against 6)
    // -> 2,4,5 (1 wins against 6).
    private void move(int i, int j, int k, int turn) {
        // possible moves:
        // - index % 2 == 0
        // - index / 2 > other index
        if (i % 2 == 1) {
            return;
        }
        int swap = i / 2;
        moveTo(swap, j + swap, k, turn);
        moveTo(swap, j, k + swap, turn);
    }

    // @tailrec
    private boolean moveTo(int i, int j, int k, int turn) {
        if (i > j) {
            return moveTo(j, i, k, turn);
        }
        if (j > k) {
            return moveTo(i, k, j, turn);
        }
        if (i < 1) {
            return false;
        }

        int val = fetch(i, j, k);
        if (val < Integer.MAX_VALUE) {
            return false;
        }
        store(i, j, k, turn);
        nextRound.add(new State(i, j, k));
        return true;
    }

    private void initializeEndstates() {
        for (int i = 1; i < maxIndex; i++) {
            for (int j = i; j < maxIndex; j++) {
                for (int k = j; k < maxIndex; k++) {
                    if ((i == j || j == k) && i + j + k <= max) {
                        store(i, j, k, 0);
                        nextRound.add(new State(i, j, k));
                    } else {
                        store(i, j, k, Integer.MAX_VALUE);
                    }
                }
            }
        }
    }

    /**
     * i,j,k should be strictly increasing.
     */
    private void store(int i, int j, int k, int val) {
        // checkArgument(i <= j);
        // checkArgument(j <= k);
        states[i][j][k] = val;
    }

    /**
     * i,j,k should be strictly increasing.
     */
    private int fetch(int i, int j, int k) {
        // checkArgument(i <= j);
        // checkArgument(j <= k);
        return states[i][j][k];
    }

    private static class State {
        final int i;
        final int j;
        final int k;

        public State(int i, int j, int k) {
            this.i = i;
            this.j = j;
            this.k = k;
        }

    }
}
