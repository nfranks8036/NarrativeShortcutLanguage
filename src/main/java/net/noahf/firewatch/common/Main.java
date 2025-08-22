package net.noahf.firewatch.common;

import net.noahf.firewatch.common.incidentsmockup.Incident;
import net.noahf.firewatch.common.shortcuts.IncidentRoots;
import net.noahf.firewatch.common.shortcuts.ShortcutParser;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        ShortcutParser parser = new ShortcutParser();
        Incident incident = new Incident();
        IncidentRoots roots = new IncidentRoots(incident);

        final String template =
//                "<INCIDENT.agencies.size == 1 ? test : <INCIDENT.agencies.size>>";
                "<INCIDENT.agencies.CONCAT>";

        String end = parser.processTemplate(template, roots);
        System.out.println("****** END ******");
        System.out.println(end);
    }

}