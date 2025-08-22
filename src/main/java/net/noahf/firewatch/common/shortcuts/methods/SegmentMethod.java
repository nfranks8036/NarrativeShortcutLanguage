package net.noahf.firewatch.common.shortcuts.methods;

import net.noahf.firewatch.common.shortcuts.SegmentContainer;
import net.noahf.firewatch.common.shortcuts.segments.Segment;

import java.util.ArrayList;
import java.util.List;

public abstract class SegmentMethod {

    private final String name;
    private final boolean isFinal;

    protected SegmentMethod(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
    }

    public abstract String apply(SegmentContainer current, List<Segment> remainingSegments, Object... params) throws Exception;

}
