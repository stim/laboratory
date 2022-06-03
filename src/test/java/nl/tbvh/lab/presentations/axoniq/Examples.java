package nl.tbvh.lab.presentations.axoniq;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class Examples {
    private List<Integer> a;
    private Sorter sorter = new Sorter();

    @Test
    public void propertyBasedTesting() {
        a = aRandomList();
        a = sorter.sort(a);
        // Any two sequential values must be strictly increasing
        for (int i = 0; i < a.size() - 1; i++) {
            assertTrue(a.get(i) <= a.get(i + 1));
        }
    }

    private List<Integer> aRandomList() {
        return asList(5, 4, 6, 3, 7);
    }

    private List<Integer> asList(Integer ... values) {
        return Arrays.asList(values);
    }

    static class Sorter {

        public List<Integer> sort(List<Integer> a) {
            ArrayList<Integer> sorted = new ArrayList<>(a);
            Collections.sort(sorted);
            return sorted;
        }
    }

}
