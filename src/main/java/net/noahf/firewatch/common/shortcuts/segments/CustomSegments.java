package net.noahf.firewatch.common.shortcuts.segments;

import net.noahf.firewatch.common.shortcuts.context.ConcatContext;

import java.sql.Array;
import java.util.*;

public class CustomSegments {

    private Map<String, Segment> segments;

    public CustomSegments() {
        this.segments = new HashMap<>();

        this.segments.put("CONCAT", (obj) -> {
            List<?> objects;
            if (obj instanceof Collection<?> collection) {
                objects = new ArrayList<>(collection);
            } else {
                objects = new ArrayList<>(List.of(obj));
            }
            return new ConcatContext(objects);
        });
    }

    public Segment get(String name) { return this.segments.get(name); }
    public boolean isValid(String name) { return this.segments.containsKey(name); }

}
