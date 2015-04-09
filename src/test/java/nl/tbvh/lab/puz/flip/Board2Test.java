package nl.tbvh.lab.puz.flip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Board2Test {
    public static Board2 createBoard(int i, int ... xs) {
        return Board2.createBoard(i, xs);
    }

    @Test
    public void allPositionsMustBeUnique() {
        Multimap<String, Board2> boards = HashMultimap.create();
        for (int x = 0; x < Board2.BOARD_MAX; x++) {
            for (int i = 0; i < Board2.BOARD_MAX; i++) {
                // System.out.println();
                // System.out.println("coin: " + i + ", x: " + x);
                Board2 board = createBoard(i, x);
                if (boards.containsKey(board.encode()) && !boards.get(board.encode()).contains(board)) {
                    fail("Duplicate board:\n" + board + "\nalready had: \n" + boards.get(board.encode()));
                }
                boards.put(board.encode(), board);
                // System.out.println(board);

            }
        }
        // printMagixSquare(boards);
    }

    private void printMagixSquare(Multimap<String, Board2> boards) {
        for (int x = 0; x < Board2.BOARD_MAX; x++) {
            for (int y = 0; y < Board2.BOARD_MAX; y++) {
                int board = 1 << x ^ 1 << y;
                Collection<Board2> bs = boards.get(Board2.encode(board));
                int coin = bs.iterator().next().coin;
                System.out.print(coin + "\t");
            }
            System.out.println();
        }
    }

    @Test
    public void decodeFindsCoin() {
        for (int i = 1; i < Board2.BOARD_MAX; i++) {
            for (int x = 0; x < Board2.BOARD_MAX; x++) {
                Board2 board = createBoard(i, x);
                // System.out.println(board);
                assertEquals("coin: " + i + ", x: " + x, i, Board2.decode(board.encode()));
            }
        }
    }

    @Test
    public void multiValueTest() {
        int[] xs = new int[] {
            3, 6, 8, 11
        };
        int coin = 13;
        Board2 board = createBoard(coin, xs);
        System.out.println(board);
        assertEquals(coin, Board2.decode(board.encode()));
    }
}
