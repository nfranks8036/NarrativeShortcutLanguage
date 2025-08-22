package net.noahf.firewatch.common.shortcuts.context;

import net.noahf.firewatch.common.shortcuts.SegmentContainer;
import net.noahf.firewatch.common.shortcuts.segments.Segment;

import java.util.List;

public class EqualsContext implements SegmentContext {

    @Override
    public String apply(SegmentContainer current, List<Segment> remainingSegments, List<Object> params) throws Exception {
        System.out.println("{{{EQUALS}}}: " + current.object().toString());
        Object first = current.object();
        Object second = this.convertIntoType(remainingSegments.getLast().apply(first));
        System.out.println("comparing " + first.toString() + " == " + second.toString());
        return String.valueOf(first.equals(second));
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    private Object convertIntoType(Object object) {
        try {
            return Integer.parseInt(object.toString());
        } catch (NumberFormatException ignored) { }

        try {
            return Double.parseDouble(object.toString());
        } catch (NumberFormatException ignored) { }

        try {
            return Boolean.parseBoolean(object.toString());
        } catch (NumberFormatException ignored) { }

        return object;
    }

}
