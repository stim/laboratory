package nl.tbvh.lab.puz.flip;

public class Board2Printer {

    public static void main(String[] args) {
        for (int x = 0; x < Board2.BOARD_MAX; x++) {
            for (int i = 0; i < Board2.BOARD_MAX; i++) {
                Board2 board = Board2.createBoard(i, x);
                System.out.print(board.encode() + "\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        for (int i = 0; i < Board2.BOARD_MAX; i++) {
            for (int x = 0; x < Board2.BOARD_MAX; x++) {
                Board2 board = Board2.createBoard(i, x);
                System.out.print(board.encode() + "\t");
            }
            System.out.println();
        }
    }

}
