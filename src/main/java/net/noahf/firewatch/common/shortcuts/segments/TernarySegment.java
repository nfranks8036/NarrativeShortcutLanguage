package net.noahf.firewatch.common.shortcuts.segments;

import java.util.List;

public class TernarySegment implements Segment {

    private final List<Segment> condition;
    private final List<Segment> trueSegment;
    private final List<Segment> falseSegment;

    public TernarySegment(List<Segment> condition, List<Segment> trueSegment, List<Segment> falseSegment) {
        this.condition = condition;
        this.trueSegment = trueSegment;
        this.falseSegment = falseSegment;
    }

    @Override
    public Object apply(Object current) throws Exception {
        Object conditionalValue = null;
        for (Segment s : condition) {
            System.out.println("TERNARY STATEMENT, on segment: " + s);
            conditionalValue = s.apply(conditionalValue);
        }

        boolean condition = false;
        System.out.println("conditional value string: " + conditionalValue);
        if (conditionalValue instanceof Boolean bool) condition = bool;
        else if (conditionalValue instanceof String str) condition = Boolean.parseBoolean(str);
        else if (conditionalValue instanceof Number num) condition = num.intValue() != 0;

        List<Segment> branch = condition ? trueSegment : falseSegment;
        Object value = current;
        for (Segment s : branch) {
            value = s.apply(value);
        }

        return value;
    }
}
