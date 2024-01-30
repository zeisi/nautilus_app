package uk.co.chrisjenx.calligraphy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectionUtils {
    ReflectionUtils() {
    }

    static Field getField(Class clazz, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    static Object getValue(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    static void setValue(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
        }
    }

    static Method getMethod(Class clazz, String methodName) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    static void invokeMethod(Object object, Method method, Object... args) {
        if (method != null) {
            try {
                method.invoke(object, args);
            } catch (IllegalAccessException | InvocationTargetException ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
