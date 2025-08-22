package net.noahf.firewatch.common.shortcuts.context;

import net.noahf.firewatch.common.shortcuts.SegmentContainer;
import net.noahf.firewatch.common.shortcuts.segments.Segment;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcatContext implements SegmentContext {

    private final List<?> data;

    public ConcatContext(List<?> data) {
        this.data = data;
    }

    @Override
    public String apply(SegmentContainer segment, List<Segment> remainingSegments, List<Object> params) throws Exception {
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
