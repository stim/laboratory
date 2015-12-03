package nl.tbvh.lab.nulls;

import org.junit.Test;


public class NullsTest {
    @Test
    public void CastOfNullDoesntThrow() {
        Object nullObj = null;

        String nullString = (String) nullObj;
    }
}