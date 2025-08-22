package net.noahf.firewatch.common.shortcuts.methods;

import net.noahf.firewatch.common.shortcuts.SegmentContainer;
import net.noahf.firewatch.common.shortcuts.segments.Segment;

import java.util.*;
import java.util.stream.Collectors;

public class ConcatMethod implements SegmentMethod {

    private final List<?> data;

    public ConcatMethod(List<?> data) {
        this.data = data;
    }

    @Override
    public String apply(SegmentContainer segment, List<Segment> remainingSegments, Object... params) throws Exception {
        List<Object> out = new ArrayList<>();
        for (Object collectionObject : this.data) {
            Object complete = collectionObject;
            for (Segment remaining : remainingSegments) {
                complete = remaining.apply(complete);
            }
            out.add(complete);
        }

        return out.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    @Override
    public boolean isFinal() {
        return true;
    }

}
