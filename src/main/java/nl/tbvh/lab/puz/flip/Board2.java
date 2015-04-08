package nl.tbvh.lab.puz.flip;

/**
 * Solution for: http://www.solipsys.co.uk/FlippingPuzzle/AnotherFlippingPuzzle.html
 */
public class Board2 {
    public static final int BOARD_SIDE = 4;
    public static final int BOARD_MAX = BOARD_SIDE * BOARD_SIDE;
    public final int coin;
    public final int board;
    private final int x;

    public Board2(int coin, int x) {
        this.coin = coin;
        this.x = x;
        board = calcBoard(coin, x);
        if (board >= 1 << BOARD_MAX) {
            System.out.println(Integer.toBinaryString(board));
            System.out.println(Integer.toBinaryString(1 << BOARD_MAX));
            fail("Board out of bounds");
        }
        if (board != 0 && (board & 1 << x) == 0) {
            fail("Board does not cover x");
        }
        int bits = Long.bitCount(board);
        if (bits != 0 && bits != 2) {
            fail("Incorrect nr. of tiles swapped.");
        }
    }

    private void fail(String msg) {
        System.out.println("Fail:");
        System.out.print(this);
        // System.out.println("Coin: " + coin + ", x: " + x);
        throw new IllegalStateException(msg);
    }

    protected int calcBoard(int coin, int x) {
        if (coin == 0) {
            return 0;
        }
        int board = 1 << x;
        int y;
        if (coin % 2 == 0) {
            y = calcY_v3(coin, x);
        } else {
            y = calcY_v1(coin, x);
        }
        // System.out.println("y: " + y);
        board |= 1 << y;
        return board;
    }

    private int calcY_v3(int coin, int x) {
        int y = x;
        int block;
        if (coin == 4 || coin == 12) {
            block = 4;
        } else {
            block = 2;
        }
        // add or subtract coin, based on what x is
        if (x / block % 2 == 0) {
            y += coin;
        } else {
            y -= coin;
        }
        return (y + BOARD_MAX) % BOARD_MAX;
    }

    private int calcY_v1(int coin, int x) {
        int y = BOARD_MAX - coin - x;
        y = (y + BOARD_MAX) % BOARD_MAX;
        if (y == x) {
            y--;
        }
        return (y + BOARD_MAX) % BOARD_MAX;
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
        str.append("coin: ").append(coin).append(", x: ").append(x);
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
        if (x < 0) {
            return 0;
        }
        int y = string.indexOf("x", x + 1);
        if (y - x % 2 == 0) {
            return y - x;
        }
        return BOARD_MAX - x - y;
    }

    public static Board2 createBoard(int i, int x) {
        return new Board2(i, x);
    }
}
