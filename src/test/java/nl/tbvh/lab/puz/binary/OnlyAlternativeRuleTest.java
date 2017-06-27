package nl.tbvh.lab.puz.binary;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Test;

import nl.tbvh.lab.puz.binary.Direction;
import nl.tbvh.lab.puz.binary.Line;
import nl.tbvh.lab.puz.binary.Move;
import nl.tbvh.lab.puz.binary.OnlyAlternativeRule;

public class OnlyAlternativeRuleTest {
    OnlyAlternativeRule ruleUT = new OnlyAlternativeRule(Direction.Horizontal);
    private List<Move> check;

    @Test
    public void checkRule() throws Exception {
        Line line = line("0 0101  ");

        check = ruleUT.check(line, '1');
        assertEquals(1, check.size());
    }

    @Test
    public void checkRule2() throws Exception {
        Line line = line("1001  0 ");

        check = ruleUT.check(line, '1');
        assertEquals(1, check.size());
    }

    @Test
    public void checkBoard() throws Exception {
        Move move = new Move(3, 0, '1', "testing");
        Board b = new NonEmptyBoard(new BoardStub(), move);

        check = ruleUT.apply(move, b);

        assertEquals(1, check.size());
    }

    private Line line(String string) {
        return new Line(1, null, string.toCharArray());
    }

    static class BoardStub extends Board {

        public BoardStub() {
            super(8);
        }

        @Override
        public char getSafely(int row, int col) {
            if (row == 3) {
                return "1001  0 ".toCharArray()[col];
            }
            return '0';
        }

    }
}
