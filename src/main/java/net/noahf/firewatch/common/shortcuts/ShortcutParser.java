package net.noahf.firewatch.common.shortcuts;

import net.noahf.firewatch.common.shortcuts.methods.ConcatMethod;
import net.noahf.firewatch.common.shortcuts.methods.EqualsMethod;
import net.noahf.firewatch.common.shortcuts.methods.SegmentMethod;
import net.noahf.firewatch.common.shortcuts.segments.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortcutParser {

    static Pattern TAG = Pattern.compile("<(.*)>");

    private final List<SegmentMethod> methods;

    private final Map<String, List<Segment>> segments;

    public ShortcutParser() {
        this.methods = new ArrayList<>();
        this.segments = new HashMap<>();

        this.methods.addAll(List.of(
                new ConcatMethod(),
                new EqualsMethod()
        ));
    }

    public String processTemplate(String template, IncidentRoots roots) {
        Matcher matcher = TAG.matcher(template);
        System.out.println("REGEX SEARCHING '" + template + "'");

        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            String regexResult = matcher.group(1);
            System.out.println("grouping '" + regexResult + "'");
            String expression = this.processTemplate(regexResult, roots);
            String result;
            try {
                List<Segment> found = this.segments.computeIfAbsent(expression, (k) -> this.findSegments(expression, roots));
                result = this.evaluate(found);
            } catch (Exception exception) {
                // if error, return the literal
                System.err.println("Failed to parse '" + expression + "' as valid narrative expression: " + exception);
                exception.printStackTrace(System.err);
                result = "<" + expression + ">";
            }
            System.out.println("REGEX COMPLETE: " + result);
            matcher.appendReplacement(builder, Matcher.quoteReplacement(result));
        }
        matcher.appendTail(builder);
        return builder.toString();
    }

    public List<Segment> findSegments(String expression, IncidentRoots roots) {
        expression = expression.trim();

        int ternaryStartIndex = expression.indexOf('?');
        if (ternaryStartIndex != -1) {
            String conditional = expression.substring(0, ternaryStartIndex).trim();
            String results = expression.substring(ternaryStartIndex + 1).trim();
            int resultSplitterCharacter = results.indexOf(':');
            if (resultSplitterCharacter == -1) {
                throw new IllegalStateException("Invalid ternary operator: no 'false' statement was provided in expression '" + expression + "'");
            }

            String trueResult = results.substring(0, resultSplitterCharacter).trim();
            String falseResult = results.substring(resultSplitterCharacter + 1).trim();

            System.out.println(conditional);
            System.out.println("true:" + trueResult);
            System.out.println("false:" + falseResult);

            List<Segment> conditionalSegments = this.findSegments(conditional, roots);
            List<Segment> trueSegments = this.findSegments(trueResult, roots);
            List<Segment> falseSegments = this.findSegments(falseResult, roots);

            return List.of(new TernarySegment(conditionalSegments, trueSegments, falseSegments));
        }

        if (!expression.contains("<") && !expression.contains(".")) {
            return List.of(new RawLiteralSegment(expression));
        }

        List<Segment> segments = new ArrayList<>();
        for (String part : expression.split("\\.")) {
            System.out.println(part);
            if (segments.isEmpty() || part.equals(part.toUpperCase())) { // custom method, ALL UPPERCASE
                System.out.println("Using custom method '" + part + "'");
                if (segments.isEmpty()) {
                    segments.add(new RootSegment(roots, part));
                    continue;
                }

                SegmentMethod method = this.methods.stream()
                        .filter(sm -> sm.name().equalsIgnoreCase(SegmentMethod.findName(part)))
                        .findFirst().orElse(null);
                if (method != null) {
                    segments.add(method);
                    continue;
                }

                throw new IllegalArgumentException("Unknown custom method/expression: " + part);
            }

            String[] possibleParams = part.split(":");
            ReflectiveMethodSegment segment;
            if (possibleParams.length <= 1) {
                segment = new ReflectiveMethodSegment(part);

            } else {
                String methodName = possibleParams[0];
                String[] params = Arrays.copyOfRange(possibleParams, 1, possibleParams.length);
                segment = new ReflectiveMethodSegment(methodName, (Object[]) params);
            }

            System.out.println("trying reflective method segment: " + part);

            segments.add(segment);
        }
        System.out.println("segments: " + segments.toString());
        return segments;
    }

    public String evaluate(List<Segment> segments) throws Exception {
        Object value = null;
        for (int i = 0; i < segments.size(); i++) {
            Segment segment = segments.get(i);
            System.out.println("segment#" + i + "::" + segment.getClass().getCanonicalName() + "::val=" + value);
            value = segment.apply(value);
            System.out.println("[end]segment#" + i + "::val=" + value);

            if (!(value instanceof SegmentMethod ctx))
                continue;

            System.out.println("Executed " + System.currentTimeMillis() + ", of " + value);
            value = ctx.apply(
                    new SegmentContainer(value, segment),
                    segments.subList(i + 1, segments.size()),
                    new ArrayList<>()
            );

            if (ctx.isFinal())
                break;
        }
        return String.valueOf(value);
    }

}
