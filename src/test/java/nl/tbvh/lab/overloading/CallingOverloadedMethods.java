package nl.tbvh.lab.overloading;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CallingOverloadedMethods {
    private String lastCalled;

    @Test
    public void overloadedMethodIsPickedCompileTime() {
        String str = "lol";
        call(str);
        assertEquals("String", lastCalled);

        Object obj = str;
        call(obj);
        assertEquals("Object", lastCalled);
    }

    private void call(Object obj) {
        lastCalled = "Object";
    }

    private void call(String obj) {
        lastCalled = "String";
    }
}
