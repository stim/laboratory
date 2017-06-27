package nl.tbvh.lab.puz.binary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Solver for http://binarypuzzle.com/
 */
public class BinaryPuzzleSolver {

    private int side;
    private Queue<Move> moves = new LinkedList<>();
    private List<RuleListener> ruleListeners = new LinkedList<>();
    private Board board;

    public BinaryPuzzleSolver(int side) {
        this.side = side;
        board = new EmptyBoard(side);
        ruleListeners.add(new AvoidTripletsRule(Direction.Horizontal));
        ruleListeners.add(new AvoidTripletsRule(Direction.Vertical));
        ruleListeners.add(new IdenticalNeighbors(Direction.Horizontal));
        ruleListeners.add(new IdenticalNeighbors(Direction.Vertical));
        ruleListeners.add(new OnlyOptionRule(Direction.Horizontal));
        ruleListeners.add(new OnlyOptionRule(Direction.Vertical));
        ruleListeners.add(new OnlyAlternativeRule(Direction.Horizontal));
        ruleListeners.add(new OnlyAlternativeRule(Direction.Vertical));
    }

    public void solve(String input) {
        try {
            readInput(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (!moves.isEmpty()) {
            Move move = moves.remove();
            System.out.println("Applying " + move);
            Board b = board.apply(move);
            if (b == null) {
                System.out.println("Skipped");
                continue;
            }

            board = b;
            apply(move);
        }
    }

    private void apply(Move move) {
        for (RuleListener rule : ruleListeners) {
            List<Move> deduced = rule.apply(move, board);
            moves.addAll(deduced);
        }
    }

    private void readInput(String input) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(input.replaceAll("[^10 ]", "")));
        // try (Scanner scanner = new Scanner(input.replaceAll("[^10 ]", ""))) {
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                char ch = (char) reader.read();
                switch (ch) {
                    case '0':
                    case '1':
                        moves.add(new Move(row, col, ch, "Puzzle Setup"));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        // http://binarypuzzle.com/puzzles.php?size=8&level=3&nr=1
        String input2 = "0  0 1  \n"
                + "  1    1\n"
                + "    0 0 \n"
                + "        \n"
                + "        \n"
                + "    00  \n"
                + "0   1 1 \n"
                + " 1 1   1";
        // http://binarypuzzle.com/puzzles.php?size=8&level=2&nr=1
        String input3 = "    0 0 \n"
                + "0 0 0 0 \n"
                + "        \n"
                + "    1 1 \n"
                + "0  0    \n"
                + "0  00   \n"
                + "       1\n"
                + " 1 1   0";
        // http://binarypuzzle.com/puzzles.php?size=8&level=3&nr=2
        String input4 = "      1 \n"
                + " 0     0\n"
                + "0  0   0\n"
                + "  1   0 \n"
                + "   0    \n"
                + "0       \n"
                + " 1 1 1  \n"
                + "0       \n";

        // http://binarypuzzle.com/puzzles.php?size=12&level=4&nr=1
        String input = "       00 1 \n"
                + "  1       1 \n"
                + "   0 0  0  0\n"
                + "     0      \n"
                + " 11 1  1 0  \n"
                + " 11 0 00    \n"
                + "          1 \n"
                + "1  00   0   \n"
                + "            \n"
                + "  1     1   \n"
                + "   0  0  0 0\n"
                + "  10   0    \n";

        BinaryPuzzleSolver solver = new BinaryPuzzleSolver(12);
        solver.solve(input);
        System.out.println();
        System.out.println(solver.board);
    }
}
