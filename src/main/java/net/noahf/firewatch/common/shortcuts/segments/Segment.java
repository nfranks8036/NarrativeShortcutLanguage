package net.noahf.firewatch.common.shortcuts.segments;

public interface Segment {

    Object apply(Object current) throws Exception;

}
