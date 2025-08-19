package net.noahf.firewatch.common.incidentsmockup;

import java.util.HashSet;
import java.util.Set;

public class Incident {

    public final Set<Agency> agenciesSet;

    public Incident() {
        this.agenciesSet = new HashSet<>();

        this.agenciesSet.add(new Agency("Blacksburg Rescue"));
        this.agenciesSet.add(new Agency("Blacksburg Fire"));
    }

    public Set<Agency> agencies() {
        return this.agenciesSet;
    }

    public String first() { return "true!!!!!"; }

    public String none() { return "not true :("; }

}
