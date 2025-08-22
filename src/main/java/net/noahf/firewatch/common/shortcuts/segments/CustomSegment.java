package net.noahf.firewatch.common.shortcuts.segments;

import net.noahf.firewatch.common.shortcuts.context.ConcatContext;
import net.noahf.firewatch.common.shortcuts.context.EqualsContext;

import java.util.*;

public record CustomSegment(String name, Segment segment, Class<?>... params) implements Segment {

    private static List<CustomSegment> segments;

    static {
        CustomSegment.segments = new ArrayList<>();

        CustomSegment.segments.add(new CustomSegment(
                "CONCAT",
                (obj) -> {
                    List<?> objects;
                    if (obj instanceof Collection<?> collection) {
                        objects = new ArrayList<>(collection);
                    } else {
                        objects = new ArrayList<>(List.of(obj));
                    }
                    return new ConcatContext(objects);
                }
        ));

        CustomSegment.segments.add(new CustomSegment(
                "EQUALS",
                (obj) -> {
                    return new EqualsContext();
                },
                Object.class
        ));
    }

    private static String normalize(String name) {
        if (name.contains(":")) {
            name = name.split(":")[0];
        }
        return name;
    }

    public static boolean isValid(String name) {
         return get(name) != null;
    }

    public static CustomSegment get(String name) {
        return segments.stream()
                .filter(cs -> cs.name.equalsIgnoreCase(normalize(name)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Object apply(Object current) throws Exception {
        return this.segment().apply(current);
    }
}
