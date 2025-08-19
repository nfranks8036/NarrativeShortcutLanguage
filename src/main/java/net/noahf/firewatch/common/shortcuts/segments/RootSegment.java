package net.noahf.firewatch.common.shortcuts.segments;

import net.noahf.firewatch.common.shortcuts.IncidentRoots;

public class RootSegment implements Segment {

    private final IncidentRoots roots;
    private final String name;

    public RootSegment(IncidentRoots roots, String name) {
        this.roots = roots;
        this.name = name;
    }

    @Override
    public Object apply(Object current) throws Exception {
        Object root = this.roots.resolveRoot(name);
        System.out.println("Root: " + root);
        return root;
    }

}
