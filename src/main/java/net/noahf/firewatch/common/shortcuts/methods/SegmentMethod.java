package net.noahf.firewatch.common.shortcuts.methods;

import net.noahf.firewatch.common.shortcuts.SegmentContainer;
import net.noahf.firewatch.common.shortcuts.segments.Segment;

import java.util.ArrayList;
import java.util.List;

public abstract class SegmentMethod implements Segment {

    public static String findName(String part) {
        if (part.contains(":")) {
            part = part.split(":")[0];
        }
        return part;
    }

    private final String name;
    private final boolean isFinal;

    protected SegmentMethod(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
    }

    public String name() { return this.name; }
    public boolean isFinal() { return this.isFinal; }

    public abstract String apply(SegmentContainer current, List<Segment> remainingSegments, Object... params) throws Exception;

}
