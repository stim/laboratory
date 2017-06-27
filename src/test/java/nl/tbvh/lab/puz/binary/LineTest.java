package nl.tbvh.lab.puz.binary;

import static org.junit.Assert.*;

import org.junit.Test;

public class LineTest {

    @Test
    public void testInvalid() throws Exception {
        Line line = line("00011011");

        boolean valid = line.valid();
        assertFalse(valid);
    }

    @Test
    public void testValid() throws Exception {
        Line line = line("00110011");

        boolean valid = line.valid();
        assertTrue(valid);
    }

    private Line line(String string) {
        return new Line(1, null, string.toCharArray());
    }
}
