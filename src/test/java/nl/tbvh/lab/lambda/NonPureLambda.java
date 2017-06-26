package nl.tbvh.lab.lambda;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.Test;

public class NonPureLambda {

    @Test
    public void mutation() {
        AtomicInteger five = new AtomicInteger(5);
        perform(() -> five.incrementAndGet());

        assertEquals(6, five.get());
    }

    private <T> void perform(Supplier<T> action) {
        action.get();
    }
}
