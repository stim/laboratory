package nl.tbvh.lab.puz.flip;

import java.util.Arrays;

/**
 * Solution for: http://www.solipsys.co.uk/FlippingPuzzle/AnotherFlippingPuzzle.html
 */
public class Board2 {
    public static final int BOARD_SIDE = 4;
    public static final int BOARD_MAX = BOARD_SIDE * BOARD_SIDE;
    public final int coin;
    public final int board;
    private final int[] xs;

    public Board2(int coin, int ... xs) {
        this.coin = coin;
        this.xs = xs;
        board = calcBoard(coin, xs);
        if (board >= 1 << BOARD_MAX) {
            System.out.println(Integer.toBinaryString(board));
            System.out.println(Integer.toBinaryString(1 << BOARD_MAX));
            fail("Board out of bounds");
        }
        int bits = Long.bitCount(board);
        if (bits == xs.length + 1) {
            for (int x : xs) {
                if (board != 0 && (board & 1 << x) == 0) {
                    fail("Board does not cover flipped " + x);
                }
            }
        } else if (bits != xs.length - 1) {
            fail("Incorrect nr. of tiles swapped.");
        }
    }

    private void fail(String msg) {
        System.out.println("Fail:");
        System.out.print(this);
        // System.out.println("Coin: " + coin + ", x: " + x);
        throw new IllegalStateException(msg);
    }

    protected int calcBoard(int coin, int ... xs) {
        if (coin == 0) {
            return 0;
        }
        int flip = coin;
        int board = 0;
        for (int x : xs) {
            flip ^= x;
            board |= 1 << x;
        }
        board ^= 1 << flip;
        return board;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < BOARD_MAX; i++) {
            int bit = 1 << i;
            if ((bit & board) > 0) {
                str.append("x");
            } else {
                str.append("o");
            }
            if ((i + 1) % BOARD_SIDE == 0) {
                str.append("\n");
            }
        }
        str.append("coin: ").append(coin).append(", flipped: ").append(Arrays.toString(xs));
        str.append("\n");
        return str.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + board;
        result = prime * result + coin;
        return result;
    }

    public String encode() {
        return encode(board);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Board2 other = (Board2) obj;
        if (board != other.board) {
            return false;
        }
        if (coin != other.coin) {
            return false;
        }
        return true;
    }

    public static String encode(int board) {
        String bits = String.format("%16s", Integer.toBinaryString(board)).replace(' ', '0');
        bits = bits.replaceAll("0", "o").replaceAll("1", "x");
        return new StringBuilder(bits).reverse().toString();
    }

    public static int decode(String string) {
        int x = string.indexOf("x");
        int value = 0;
        while (x >= 0) {
            value ^= x;
            x = string.indexOf("x", x + 1);
        }
        return value;
    }

    public static Board2 createBoard(int i, int ... xs) {
        return new Board2(i, xs);
    }
}
