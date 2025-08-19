package net.noahf.firewatch.common.shortcuts;

import net.noahf.firewatch.common.shortcuts.segments.Segment;

public class SegmentContainer {

    private final Object object;
    private final Segment segment;

    public SegmentContainer(Object object, Segment segment) {
        this.object = object;
        this.segment = segment;
    }

    public Object object() { return this.object; }
    public Segment segment() { return this.segment; }

}
