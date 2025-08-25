package net.noahf.firewatch.common.shortcuts.methods;

import net.noahf.firewatch.common.shortcuts.SegmentContainer;
import net.noahf.firewatch.common.shortcuts.segments.Segment;

import java.util.*;
import java.util.stream.Collectors;

public class ConcatMethod extends SegmentMethod {

    public ConcatMethod() {
        super("CONCAT", true);
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
    public Object apply(Object current) throws Exception {
        List<?> objects;
        if (current instanceof Collection<?> collection) {
            objects = new ArrayList<>(collection);
        } else {
            objects = new ArrayList<>(List.of(current));
        }

        return objects;
    }
}
