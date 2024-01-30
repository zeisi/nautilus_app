package com.mobsandgeeks.saripaar;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;
import com.mobsandgeeks.saripaar.exception.ConversionException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ValidationContext {
    private Context mContext;
    Map<View, ArrayList<Pair<Rule, ViewDataAdapter>>> mViewRulesMap;

    ValidationContext(Context context) {
        this.mContext = context;
    }

    public List<View> getAnnotatedViews(Class<? extends Annotation> saripaarAnnotation) {
        assertNotNull(saripaarAnnotation, "saripaarAnnotation");
        assertIsRegisteredAnnotation(saripaarAnnotation);
        Class<? extends AnnotationRule> annotationRuleClass = getRuleClass(saripaarAnnotation);
        List<View> annotatedViews = new ArrayList<>();
        for (View view : this.mViewRulesMap.keySet()) {
            Iterator<Pair<Rule, ViewDataAdapter>> it = this.mViewRulesMap.get(view).iterator();
            while (it.hasNext()) {
                if (annotationRuleClass.equals(((Rule) it.next().first).getClass()) && !annotatedViews.contains(view)) {
                    annotatedViews.add(view);
                }
            }
        }
        return annotatedViews;
    }

    public Object getData(View view, Class<? extends Annotation> saripaarAnnotation) {
        assertNotNull(view, "view");
        assertNotNull(saripaarAnnotation, "saripaarAnnotation");
        Object data = null;
        Class<? extends AnnotationRule> annotationRuleClass = getRuleClass(saripaarAnnotation);
        Iterator<Pair<Rule, ViewDataAdapter>> it = this.mViewRulesMap.get(view).iterator();
        while (it.hasNext()) {
            Pair<Rule, ViewDataAdapter> ruleAdapterPair = it.next();
            if (annotationRuleClass.equals(((Rule) ruleAdapterPair.first).getClass())) {
                try {
                    data = ((ViewDataAdapter) ruleAdapterPair.second).getData(view);
                } catch (ConversionException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: package-private */
    public void setViewRulesMap(Map<View, ArrayList<Pair<Rule, ViewDataAdapter>>> viewRulesMap) {
        this.mViewRulesMap = viewRulesMap;
    }

    private void assertNotNull(Object object, String argumentName) {
        if (object == null) {
            throw new IllegalArgumentException(String.format("'%s' cannot be null.", new Object[]{argumentName}));
        }
    }

    private void assertIsRegisteredAnnotation(Class<? extends Annotation> saripaarAnnotation) {
        if (!Validator.isSaripaarAnnotation(saripaarAnnotation)) {
            throw new IllegalArgumentException(String.format("%s is not a registered Saripaar annotation.", new Object[]{saripaarAnnotation.getName()}));
        }
    }

    private Class<? extends AnnotationRule> getRuleClass(Class<? extends Annotation> saripaarAnnotation) {
        return ((ValidateUsing) saripaarAnnotation.getAnnotation(ValidateUsing.class)).value();
    }
}
