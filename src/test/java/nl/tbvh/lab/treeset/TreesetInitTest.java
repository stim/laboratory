package nl.tbvh.lab.treeset;

import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

import org.junit.Test;

public class TreesetInitTest {
    private GapAwareTrackingToken tokenUT;

    private long nextIndex;
    private Random rnd = new Random();

    @Test
    public void triggerTheError() throws Exception {
        TreeSet<Long> gaps = new TreeSet<>();
        gaps.add(-55L);
        gaps.add(null);
        tokenUT = GapAwareTrackingToken.newInstance(0, gaps);

        int progress = 10000;
        while (true) {
            if (rnd.nextDouble() < .1) {
                tokenUT = GapAwareTrackingToken.newInstance(tokenUT.index, tokenUT.gaps);
            }
            tokenUT = tokenUT.advanceTo(getNextIndex(), 1000, true);
            if (progress-- == 0) {
                progress = 10000;
                System.out.print(".");
            }
        }
    }

    private long getNextIndex() {
        if (rnd.nextDouble() < .02) {
            nextIndex += 1 + rnd.nextInt(100);
        } else if (rnd.nextDouble() < .9) {
            if (tokenUT.gaps.isEmpty()) {
                nextIndex++;
            } else {
                return (long) tokenUT.gaps.toArray()[rnd.nextInt(tokenUT.gaps.size())];
            }
        } else {
            nextIndex++;
        }
        return nextIndex;
    }
}
