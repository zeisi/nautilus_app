package com.mobsandgeeks.saripaar;

import android.view.View;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;
import com.mobsandgeeks.saripaar.exception.SaripaarViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

final class Reflector {
    public static Method getAttributeMethod(Class<? extends Annotation> annotationType, String attributeName) {
        try {
            return annotationType.getMethod(attributeName, new Class[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getAttributeValue(Annotation annotation, String attributeName, Class<T> attributeDataType) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Method attributeMethod = getAttributeMethod(annotationType, attributeName);
        if (attributeMethod == null) {
            throw new IllegalStateException(String.format("Cannot find attribute '%s' in annotation '%s'.", new Object[]{attributeName, annotationType.getName()}));
        }
        try {
            T result = attributeMethod.invoke(annotation, new Object[0]);
            if (attributeDataType.isPrimitive()) {
                return result;
            }
            return attributeDataType.cast(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean isAnnotated(Class<? extends Annotation> inspected, Class<? extends Annotation> expected) {
        boolean isAnnotated = false;
        for (Annotation declaredAnnotation : inspected.getDeclaredAnnotations()) {
            isAnnotated = expected.equals(declaredAnnotation.annotationType());
            if (isAnnotated) {
                break;
            }
        }
        return isAnnotated;
    }

    public static Method findGetDataMethod(Class<? extends ViewDataAdapter> dataAdapterType) {
        boolean nonVolatile;
        boolean hasSingleViewParameter;
        for (Method method : dataAdapterType.getDeclaredMethods()) {
            if ("getData".equals(method.getName())) {
                if (!Modifier.isVolatile(method.getModifiers())) {
                    nonVolatile = true;
                } else {
                    nonVolatile = false;
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1 || !View.class.isAssignableFrom(parameterTypes[0])) {
                    hasSingleViewParameter = false;
                } else {
                    hasSingleViewParameter = true;
                }
                if (nonVolatile && hasSingleViewParameter) {
                    return method;
                }
            }
        }
        return null;
    }

    public static AnnotationRule instantiateRule(Class<? extends AnnotationRule> ruleType, Annotation ruleAnnotation, ValidationContext validationContext) throws SaripaarViolationException {
        try {
            if (ContextualAnnotationRule.class.isAssignableFrom(ruleType)) {
                Constructor<? extends AnnotationRule> declaredConstructor = ruleType.getDeclaredConstructor(new Class[]{ValidationContext.class, ruleAnnotation.annotationType()});
                declaredConstructor.setAccessible(true);
                return (AnnotationRule) declaredConstructor.newInstance(new Object[]{validationContext, ruleAnnotation});
            } else if (!AnnotationRule.class.isAssignableFrom(ruleType)) {
                return null;
            } else {
                Constructor<? extends AnnotationRule> declaredConstructor2 = ruleType.getDeclaredConstructor(new Class[]{ruleAnnotation.annotationType()});
                declaredConstructor2.setAccessible(true);
                return (AnnotationRule) declaredConstructor2.newInstance(new Object[]{ruleAnnotation});
            }
        } catch (NoSuchMethodException e) {
            throw new SaripaarViolationException(getMissingConstructorErrorMessage(ruleType, ruleAnnotation.annotationType()));
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return null;
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static Class<?> getRuleDataType(Annotation ruleAnnotation) {
        return getRuleDataType(getValidateUsingAnnotation(ruleAnnotation.annotationType()));
    }

    public static Class<?> getRuleDataType(ValidateUsing validateUsing) {
        Class<? extends AnnotationRule> rule = validateUsing.value();
        return getRuleTypeFromIsValidMethod(rule, rule.getDeclaredMethods());
    }

    private static ValidateUsing getValidateUsingAnnotation(Class<? extends Annotation> annotationType) {
        for (Annotation annotation : annotationType.getDeclaredAnnotations()) {
            if (ValidateUsing.class.equals(annotation.annotationType())) {
                return (ValidateUsing) annotation;
            }
        }
        return null;
    }

    private static String getMissingConstructorErrorMessage(Class<? extends AnnotationRule> ruleType, Class<? extends Annotation> annotationType) {
        if (ContextualAnnotationRule.class.isAssignableFrom(ruleType)) {
            return String.format("A constructor accepting a '%s' and a '%s' is required for %s.", new Object[]{ValidationContext.class, annotationType.getName(), ruleType.getClass().getName()});
        } else if (!AnnotationRule.class.isAssignableFrom(ruleType)) {
            return null;
        } else {
            return String.format("'%s' should have a single-argument constructor that accepts a '%s' instance.", new Object[]{ruleType.getName(), annotationType.getName()});
        }
    }

    private static Class<?> getRuleTypeFromIsValidMethod(Class<? extends AnnotationRule> rule, Method[] methods) {
        Class<?> returnType = null;
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (matchesIsValidMethodSignature(method, parameterTypes)) {
                if (returnType != null) {
                    throw new SaripaarViolationException(String.format("Found duplicate 'boolean isValid(T)' method signature in '%s'.", new Object[]{rule.getName()}));
                }
                returnType = parameterTypes[0];
            }
        }
        return returnType;
    }

    private static boolean matchesIsValidMethodSignature(Method method, Class<?>[] parameterTypes) {
        boolean nonVolatile;
        boolean hasSingleParameter;
        int modifiers = method.getModifiers();
        boolean isPublic = Modifier.isPublic(modifiers);
        if (!Modifier.isVolatile(modifiers)) {
            nonVolatile = true;
        } else {
            nonVolatile = false;
        }
        boolean returnsBoolean = Boolean.TYPE.equals(method.getReturnType());
        boolean matchesMethodName = "isValid".equals(method.getName());
        if (parameterTypes.length == 1) {
            hasSingleParameter = true;
        } else {
            hasSingleParameter = false;
        }
        if (!isPublic || !nonVolatile || !returnsBoolean || !matchesMethodName || !hasSingleParameter) {
            return false;
        }
        return true;
    }

    private Reflector() {
    }
}
