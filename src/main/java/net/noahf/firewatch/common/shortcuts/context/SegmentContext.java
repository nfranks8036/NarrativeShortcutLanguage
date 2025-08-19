package net.noahf.firewatch.common.shortcuts.context;

import net.noahf.firewatch.common.shortcuts.SegmentContainer;
import net.noahf.firewatch.common.shortcuts.segments.Segment;

import java.util.List;

public interface SegmentContext {

    String apply(SegmentContainer current, List<Segment> remainingSegments) throws Exception;

    boolean isFinal();

}
