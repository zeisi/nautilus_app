package com.mobsandgeeks.saripaar;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.mobsandgeeks.saripaar.adapter.CheckBoxBooleanAdapter;
import com.mobsandgeeks.saripaar.adapter.RadioButtonBooleanAdapter;
import com.mobsandgeeks.saripaar.adapter.RadioGroupBooleanAdapter;
import com.mobsandgeeks.saripaar.adapter.SpinnerIndexAdapter;
import com.mobsandgeeks.saripaar.adapter.TextViewDoubleAdapter;
import com.mobsandgeeks.saripaar.adapter.TextViewFloatAdapter;
import com.mobsandgeeks.saripaar.adapter.TextViewIntegerAdapter;
import com.mobsandgeeks.saripaar.adapter.TextViewStringAdapter;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;
import com.mobsandgeeks.saripaar.exception.SaripaarViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class Registry {
    private static final Map<Class<? extends View>, HashMap<Class<?>, ViewDataAdapter>> STOCK_ADAPTERS = new HashMap();
    public static final String TAG = "Registry";
    private Map<Class<? extends Annotation>, HashMap<Class<? extends View>, ViewDataAdapter>> mMappings = new HashMap();

    static {
        HashMap<Class<?>, ViewDataAdapter> adapters = new HashMap<>();
        adapters.put(Boolean.class, new CheckBoxBooleanAdapter());
        STOCK_ADAPTERS.put(CheckBox.class, adapters);
        HashMap<Class<?>, ViewDataAdapter> adapters2 = new HashMap<>();
        adapters2.put(Boolean.class, new RadioButtonBooleanAdapter());
        STOCK_ADAPTERS.put(RadioButton.class, adapters2);
        HashMap<Class<?>, ViewDataAdapter> adapters3 = new HashMap<>();
        adapters3.put(Boolean.class, new RadioGroupBooleanAdapter());
        STOCK_ADAPTERS.put(RadioGroup.class, adapters3);
        HashMap<Class<?>, ViewDataAdapter> adapters4 = new HashMap<>();
        adapters4.put(Integer.class, new SpinnerIndexAdapter());
        STOCK_ADAPTERS.put(Spinner.class, adapters4);
        HashMap<Class<?>, ViewDataAdapter> adapters5 = new HashMap<>();
        adapters5.put(String.class, new TextViewStringAdapter());
        adapters5.put(Integer.class, new TextViewIntegerAdapter());
        adapters5.put(Float.class, new TextViewFloatAdapter());
        adapters5.put(Double.class, new TextViewDoubleAdapter());
        STOCK_ADAPTERS.put(TextView.class, adapters5);
    }

    Registry() {
    }

    public void register(Class<? extends Annotation>... ruleAnnotations) {
        for (Class<? extends Annotation> ruleAnnotation : ruleAnnotations) {
            assertIsValidRuleAnnotation(ruleAnnotation);
            Class<?> ruleDataType = Reflector.getRuleDataType((ValidateUsing) ruleAnnotation.getAnnotation(ValidateUsing.class));
            HashMap<Class<?>, ViewDataAdapter> viewDataAdapterMap = STOCK_ADAPTERS.get(TextView.class);
            if (viewDataAdapterMap != null) {
                ViewDataAdapter dataAdapter = viewDataAdapterMap.get(ruleDataType);
                if (dataAdapter != null) {
                    register(TextView.class, ruleDataType, dataAdapter, (Class<? extends Annotation>[]) new Class[]{ruleAnnotation});
                } else {
                    throw new SaripaarViolationException(String.format("Unable to find a matching adapter for `%s`, that returns a `%s`.", new Object[]{ruleAnnotation.getName(), ruleDataType.getName()}));
                }
            }
        }
    }

    public <VIEW extends View, DATA_TYPE> void register(Class<VIEW> viewType, Class<DATA_TYPE> ruleDataType, ViewDataAdapter<VIEW, DATA_TYPE> viewDataAdapter, Class<? extends Annotation>... ruleAnnotations) {
        if (ruleAnnotations != null && ruleAnnotations.length > 0) {
            for (Class<? extends Annotation> ruleAnnotation : ruleAnnotations) {
                register(ruleAnnotation, ruleDataType, viewType, viewDataAdapter);
            }
        }
    }

    public Set<Class<? extends Annotation>> getRegisteredAnnotations() {
        return this.mMappings.keySet();
    }

    public <VIEW extends View> ViewDataAdapter<VIEW, ?> getDataAdapter(Class<? extends Annotation> annotationType, Class<VIEW> viewType) {
        HashMap<Class<? extends View>, ViewDataAdapter> viewDataAdapterHashMap = this.mMappings.get(annotationType);
        if (viewDataAdapterHashMap == null) {
            return null;
        }
        ViewDataAdapter matchingViewAdapter = viewDataAdapterHashMap.get(viewType);
        if (matchingViewAdapter == null) {
            return getCompatibleViewDataAdapter(viewDataAdapterHashMap, viewType);
        }
        return matchingViewAdapter;
    }

    private <VIEW extends View, DATA_TYPE> void register(Class<? extends Annotation> ruleAnnotation, Class<DATA_TYPE> ruleDataType, Class<VIEW> view, ViewDataAdapter<VIEW, DATA_TYPE> viewDataAdapter) {
        HashMap<Class<? extends View>, ViewDataAdapter> viewAdapterPairs;
        assertIsValidRuleAnnotation(ruleAnnotation);
        assertCompatibleReturnType(ruleDataType, viewDataAdapter);
        if (this.mMappings.containsKey(ruleAnnotation)) {
            viewAdapterPairs = this.mMappings.get(ruleAnnotation);
        } else {
            viewAdapterPairs = new HashMap<>();
            this.mMappings.put(ruleAnnotation, viewAdapterPairs);
        }
        if (viewAdapterPairs.containsKey(view)) {
            Log.w(TAG, String.format("A '%s' for '%s' has already been registered.", new Object[]{ruleAnnotation.getName(), view.getName()}));
            return;
        }
        viewAdapterPairs.put(view, viewDataAdapter);
    }

    private void assertIsValidRuleAnnotation(Class<? extends Annotation> ruleAnnotation) {
        if (!Reflector.isAnnotated(ruleAnnotation, ValidateUsing.class)) {
            throw new IllegalArgumentException(String.format("'%s' is not annotated with '%s'.", new Object[]{ruleAnnotation.getName(), ValidateUsing.class.getName()}));
        }
        assertAttribute(ruleAnnotation, "sequence", Integer.TYPE);
        assertAttribute(ruleAnnotation, "message", String.class);
        assertAttribute(ruleAnnotation, "messageResId", Integer.TYPE);
    }

    private void assertAttribute(Class<? extends Annotation> annotationType, String attributeName, Class<?> attributeType) {
        Method attributeMethod = Reflector.getAttributeMethod(annotationType, attributeName);
        if (attributeMethod == null) {
            throw new SaripaarViolationException(String.format("'%s' requires the '%s' attribute.", new Object[]{annotationType.getName(), attributeName}));
        }
        Class<?> returnType = attributeMethod.getReturnType();
        if (!attributeType.equals(returnType)) {
            throw new SaripaarViolationException(String.format("'%s' in '%s' should be of type '%s', but was '%s'.", new Object[]{attributeName, annotationType.getName(), attributeType.getName(), returnType.getName()}));
        }
    }

    private <DATA_TYPE, VIEW extends View> void assertCompatibleReturnType(Class<DATA_TYPE> ruleDataType, ViewDataAdapter<VIEW, DATA_TYPE> viewDataAdapter) {
        Class<?> adapterReturnType = Reflector.findGetDataMethod(viewDataAdapter.getClass()).getReturnType();
        if (!ruleDataType.equals(adapterReturnType)) {
            throw new IllegalArgumentException(String.format("'%s' returns '%s', but expecting '%s'.", new Object[]{viewDataAdapter.getClass().getName(), adapterReturnType.getName(), ruleDataType.getName()}));
        }
    }

    private <VIEW extends View> ViewDataAdapter getCompatibleViewDataAdapter(HashMap<Class<? extends View>, ViewDataAdapter> viewDataAdapterHashMap, Class<VIEW> viewType) {
        ViewDataAdapter compatibleViewAdapter = null;
        for (Class<? extends View> registeredView : viewDataAdapterHashMap.keySet()) {
            if (registeredView.isAssignableFrom(viewType)) {
                compatibleViewAdapter = viewDataAdapterHashMap.get(registeredView);
            }
        }
        return compatibleViewAdapter;
    }
}
