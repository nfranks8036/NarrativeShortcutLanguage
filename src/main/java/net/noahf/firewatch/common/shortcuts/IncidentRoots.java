package net.noahf.firewatch.common.shortcuts;

import net.noahf.firewatch.common.incidentsmockup.Incident;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class IncidentRoots {

    private final Map<String, Supplier<Object>> roots;

    public IncidentRoots(Incident incident) {
        this.roots = new HashMap<>();

        this.roots.put("INCIDENT", () -> incident);
    }

    public Object resolveRoot(String name) {
        return this.roots.get(name).get();
    }

}
