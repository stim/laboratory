package nl.tbvh.lab.treeset;

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.LongStream;


public class GapAwareTrackingToken {
    public final long index;
    public final SortedSet<Long> gaps;

    public static GapAwareTrackingToken newInstance(
            long index,
            Collection<Long> gaps) {

        if (gaps.isEmpty()) {
            return new GapAwareTrackingToken(index, Collections.emptySortedSet());
        }
        SortedSet<Long> gapSet = new TreeSet<>(gaps);
        checkState(gapSet.last() < index,
            String.format("Gap indices [%s] should all be smaller than head index [%d]", gaps, index));
        return new GapAwareTrackingToken(index, gapSet);
    }

    private GapAwareTrackingToken(long index, SortedSet<Long> gaps) {
        this.index = index;
        this.gaps = gaps;
    }


    public GapAwareTrackingToken advanceTo(long index, int maxGapOffset, boolean allowGaps) {
        long newIndex;
        SortedSet<Long> gaps = new TreeSet<>(this.gaps);
        if (gaps.remove(index)) {
            newIndex = this.index;
        } else if (index > this.index) {
            newIndex = index;
            LongStream.range(this.index + 1L, index).forEach(gaps::add);
        } else {
            throw new IllegalArgumentException(String.format(
                "The given index [%d] should be larger than the token index [%d] or be one of the token's gaps [%s]",
                index, this.index, gaps));
        }
        long smalledAllowedGap = allowGaps ? (newIndex - maxGapOffset) : Math.max(index, newIndex - maxGapOffset);
        gaps = gaps.tailSet(smalledAllowedGap);
        return new GapAwareTrackingToken(newIndex, gaps);
    }
}
