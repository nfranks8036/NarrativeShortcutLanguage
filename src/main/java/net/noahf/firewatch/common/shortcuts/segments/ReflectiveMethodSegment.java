package net.noahf.firewatch.common.shortcuts.segments;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

public class ReflectiveMethodSegment implements Segment {

    private final String name;
    private final Object[] params;

    public ReflectiveMethodSegment(String name, Object... params) {
        this.name = name;
        this.params = Arrays.copyOf(params, params.length, Object[].class);
    }

    @Override
    public Object apply(Object current) throws Exception {
        System.out.println("current=" + current);

        Class<?>[] classes = new Class[this.params.length];
        for (int i = 0; i < this.params.length; i++) {
            Object param = this.params[i];
            String stringParam = String.valueOf(param);

            try {
                this.params[i] = Integer.parseInt(stringParam);
                classes[i] = int.class;
                continue;
            } catch (NumberFormatException ignored) { }

            try {
                this.params[i] = Double.parseDouble(stringParam);
                classes[i] = double.class;
                continue;
            } catch (NumberFormatException ignored) { }

            try {
                this.params[i] = Boolean.parseBoolean(stringParam);
                classes[i] = boolean.class;
                continue;
            } catch (NumberFormatException ignored) { }

            this.params[i] = String.valueOf(stringParam);
            classes[i] = String.class;
        }

        System.out.println("Finding method in " + current.getClass().getCanonicalName() + ": " + this.name + "(" + Arrays.stream(classes).map(Class::getCanonicalName).toArray().toString() + ")");
        Object value = this.tryMethod(current, current.getClass(), this.name, classes);
        return value instanceof EmptyResponse ? null : value;
    }

    private Object tryMethod(Object object, Class<?> clazz, String methodName, Class<?>... params) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, params);
            method.setAccessible(true);
            return method.invoke(object, this.params);
        } catch (Exception exception) {
            System.err.println("From: " + clazz.getCanonicalName());
            exception.printStackTrace(System.err);
            return new EmptyResponse();
        }
    }

    private static class EmptyResponse {

    }

}
