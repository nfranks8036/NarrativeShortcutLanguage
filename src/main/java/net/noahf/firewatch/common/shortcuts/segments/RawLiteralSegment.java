package net.noahf.firewatch.common.shortcuts.segments;

public record RawLiteralSegment(String literal) implements Segment {

    @Override
    public Object apply(Object current) throws Exception {
        return this.literal;
    }

}
