package com.mobsandgeeks.saripaar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.mobsandgeeks.saripaar.adapter.CheckBoxBooleanAdapter;
import com.mobsandgeeks.saripaar.adapter.RadioButtonBooleanAdapter;
import com.mobsandgeeks.saripaar.adapter.RadioGroupBooleanAdapter;
import com.mobsandgeeks.saripaar.adapter.SpinnerIndexAdapter;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.annotation.AssertFalse;
import com.mobsandgeeks.saripaar.annotation.AssertTrue;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmEmail;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.CreditCard;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.Domain;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Future;
import com.mobsandgeeks.saripaar.annotation.IpAddress;
import com.mobsandgeeks.saripaar.annotation.Isbn;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Past;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.mobsandgeeks.saripaar.annotation.Select;
import com.mobsandgeeks.saripaar.annotation.Url;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;
import com.mobsandgeeks.saripaar.exception.ConversionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Validator {
    private static final Registry SARIPAAR_REGISTRY = new Registry();
    private AsyncValidationTask mAsyncValidationTask;
    private Object mController;
    private boolean mOrderedFields;
    private final Map<Class<? extends View>, HashMap<Class<?>, ViewDataAdapter>> mRegisteredAdaptersMap = new HashMap();
    private SequenceComparator mSequenceComparator;
    private ValidationContext mValidationContext;
    private ValidationListener mValidationListener;
    private Mode mValidationMode;
    private Map<View, ArrayList<Pair<Rule, ViewDataAdapter>>> mViewRulesMap;
    private ViewValidatedAction mViewValidatedAction;
    private Handler mViewValidatedActionHandler;

    public enum Mode {
        BURST,
        IMMEDIATE
    }

    public interface ValidationListener {
        void onValidationFailed(List<ValidationError> list);

        void onValidationSucceeded();
    }

    public interface ViewValidatedAction {
        void onAllRulesPassed(View view);
    }

    static {
        SARIPAAR_REGISTRY.register(CheckBox.class, Boolean.class, new CheckBoxBooleanAdapter(), (Class<? extends Annotation>[]) new Class[]{AssertFalse.class, AssertTrue.class, Checked.class});
        SARIPAAR_REGISTRY.register(RadioGroup.class, Boolean.class, new RadioGroupBooleanAdapter(), (Class<? extends Annotation>[]) new Class[]{Checked.class});
        SARIPAAR_REGISTRY.register(RadioButton.class, Boolean.class, new RadioButtonBooleanAdapter(), (Class<? extends Annotation>[]) new Class[]{AssertFalse.class, AssertTrue.class, Checked.class});
        SARIPAAR_REGISTRY.register(Spinner.class, Integer.class, new SpinnerIndexAdapter(), (Class<? extends Annotation>[]) new Class[]{Select.class});
        SARIPAAR_REGISTRY.register(DecimalMax.class, DecimalMin.class);
        SARIPAAR_REGISTRY.register(Max.class, Min.class);
        SARIPAAR_REGISTRY.register(ConfirmEmail.class, ConfirmPassword.class, CreditCard.class, Digits.class, Domain.class, Email.class, Future.class, IpAddress.class, Isbn.class, Length.class, NotEmpty.class, Password.class, Past.class, Pattern.class, Url.class);
    }

    public Validator(Object controller) {
        assertNotNull(controller, "controller");
        this.mController = controller;
        this.mValidationMode = Mode.BURST;
        this.mSequenceComparator = new SequenceComparator();
        this.mViewValidatedAction = new DefaultViewValidatedAction();
        if (controller instanceof Activity) {
            this.mValidationContext = new ValidationContext((Activity) controller);
        } else if (Build.VERSION.SDK_INT >= 11 && (controller instanceof Fragment)) {
            this.mValidationContext = new ValidationContext(((Fragment) controller).getActivity());
        }
    }

    public static void registerAnnotation(Class<? extends Annotation> ruleAnnotation) {
        SARIPAAR_REGISTRY.register(ruleAnnotation);
    }

    public static <VIEW extends View> void registerAnnotation(Class<? extends Annotation> annotation, Class<VIEW> viewType, ViewDataAdapter<VIEW, ?> viewDataAdapter) {
        Class ruleDataType = Reflector.getRuleDataType((ValidateUsing) annotation.getAnnotation(ValidateUsing.class));
        SARIPAAR_REGISTRY.register(viewType, ruleDataType, viewDataAdapter, (Class<? extends Annotation>[]) new Class[]{annotation});
    }

    public <VIEW extends View, DATA_TYPE> void registerAdapter(Class<VIEW> viewType, ViewDataAdapter<VIEW, DATA_TYPE> viewDataAdapter) {
        assertNotNull(viewType, "viewType");
        assertNotNull(viewDataAdapter, "viewDataAdapter");
        HashMap<Class<?>, ViewDataAdapter> dataTypeAdapterMap = this.mRegisteredAdaptersMap.get(viewType);
        if (dataTypeAdapterMap == null) {
            dataTypeAdapterMap = new HashMap<>();
            this.mRegisteredAdaptersMap.put(viewType, dataTypeAdapterMap);
        }
        dataTypeAdapterMap.put(Reflector.findGetDataMethod(viewDataAdapter.getClass()).getReturnType(), viewDataAdapter);
    }

    public void setValidationListener(ValidationListener validationListener) {
        assertNotNull(validationListener, "validationListener");
        this.mValidationListener = validationListener;
    }

    public void setViewValidatedAction(ViewValidatedAction viewValidatedAction) {
        this.mViewValidatedAction = viewValidatedAction;
    }

    public void setValidationMode(Mode validationMode) {
        assertNotNull(validationMode, "validationMode");
        this.mValidationMode = validationMode;
    }

    public Mode getValidationMode() {
        return this.mValidationMode;
    }

    public void validate() {
        validate(false);
    }

    public void validateBefore(View view) {
        validateBefore(view, false);
    }

    public void validateTill(View view) {
        validateTill(view, false);
    }

    public void validate(boolean async) {
        createRulesSafelyAndLazily(false);
        View lastView = getLastView();
        if (Mode.BURST.equals(this.mValidationMode)) {
            validateUnorderedFieldsWithCallbackTill(lastView, async);
        } else if (Mode.IMMEDIATE.equals(this.mValidationMode)) {
            validateOrderedFieldsWithCallbackTill(lastView, String.format("in %s mode.", new Object[]{Mode.IMMEDIATE.toString()}), async);
        } else {
            throw new RuntimeException("This should never happen!");
        }
    }

    public void validateBefore(View view, boolean async) {
        createRulesSafelyAndLazily(false);
        validateOrderedFieldsWithCallbackTill(getViewBefore(view), "when using 'validateBefore(View)'.", async);
    }

    public void validateTill(View view, boolean async) {
        validateOrderedFieldsWithCallbackTill(view, "when using 'validateTill(View)'.", async);
    }

    public boolean isValidating() {
        return (this.mAsyncValidationTask == null || this.mAsyncValidationTask.getStatus() == AsyncTask.Status.FINISHED) ? false : true;
    }

    public boolean cancelAsync() {
        if (this.mAsyncValidationTask == null) {
            return false;
        }
        boolean cancelled = this.mAsyncValidationTask.cancel(true);
        this.mAsyncValidationTask = null;
        return cancelled;
    }

    public <VIEW extends View> void put(VIEW view, QuickRule<VIEW>... quickRules) {
        assertNotNull(view, "view");
        assertNotNull(quickRules, "quickRules");
        if (quickRules.length == 0) {
            throw new IllegalArgumentException("'quickRules' cannot be empty.");
        }
        if (this.mValidationContext == null) {
            this.mValidationContext = new ValidationContext(view.getContext());
        }
        createRulesSafelyAndLazily(true);
        if (!this.mOrderedFields || this.mViewRulesMap.containsKey(view)) {
            ArrayList<Pair<Rule, ViewDataAdapter>> ruleAdapterPairs = this.mViewRulesMap.get(view);
            if (ruleAdapterPairs == null) {
                ruleAdapterPairs = new ArrayList<>();
            }
            for (QuickRule quickRule : quickRules) {
                if (quickRule != null) {
                    ruleAdapterPairs.add(new Pair(quickRule, (Object) null));
                }
            }
            Collections.sort(ruleAdapterPairs, this.mSequenceComparator);
            this.mViewRulesMap.put(view, ruleAdapterPairs);
            return;
        }
        throw new IllegalStateException(String.format("All fields are ordered, so this `%s` should be ordered too, declare the view as a field and add the `@Order` annotation.", new Object[]{view.getClass().getName()}));
    }

    public void removeRules(View view) {
        assertNotNull(view, "view");
        if (this.mViewRulesMap == null) {
            createRulesSafelyAndLazily(false);
        }
        this.mViewRulesMap.remove(view);
    }

    static boolean isSaripaarAnnotation(Class<? extends Annotation> annotation) {
        return SARIPAAR_REGISTRY.getRegisteredAnnotations().contains(annotation);
    }

    private static void assertNotNull(Object object, String argumentName) {
        if (object == null) {
            throw new IllegalArgumentException(String.format("'%s' cannot be null.", new Object[]{argumentName}));
        }
    }

    private void createRulesSafelyAndLazily(boolean addingQuickRules) {
        if (this.mViewRulesMap == null) {
            this.mViewRulesMap = createRules(getSaripaarAnnotatedFields(this.mController.getClass()));
            this.mValidationContext.setViewRulesMap(this.mViewRulesMap);
        }
        if (!addingQuickRules && this.mViewRulesMap.size() == 0) {
            throw new IllegalStateException("No rules found. You must have at least one rule to validate. If you are using custom annotations, make sure that you have registered them using the 'Validator.register()' method.");
        }
    }

    private List<Field> getSaripaarAnnotatedFields(Class<?> controllerClass) {
        boolean z;
        Set<Class<? extends Annotation>> saripaarAnnotations = SARIPAAR_REGISTRY.getRegisteredAnnotations();
        List<Field> annotatedFields = new ArrayList<>();
        for (Field field : getControllerViewFields(controllerClass)) {
            if (isSaripaarAnnotatedField(field, saripaarAnnotations)) {
                annotatedFields.add(field);
            }
        }
        SaripaarFieldsComparator comparator = new SaripaarFieldsComparator();
        Collections.sort(annotatedFields, comparator);
        if (annotatedFields.size() == 1) {
            z = annotatedFields.get(0).getAnnotation(Order.class) != null;
        } else {
            z = annotatedFields.size() != 0 && comparator.areOrderedFields();
        }
        this.mOrderedFields = z;
        return annotatedFields;
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.reflect.Field> getControllerViewFields(java.lang.Class<?> r5) {
        /*
            r4 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r3 = r4.getViewFields(r5)
            r0.addAll(r3)
            java.lang.Class r1 = r5.getSuperclass()
        L_0x0010:
            java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x002a
            java.util.List r2 = r4.getViewFields(r1)
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x0025
            r0.addAll(r2)
        L_0x0025:
            java.lang.Class r1 = r1.getSuperclass()
            goto L_0x0010
        L_0x002a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobsandgeeks.saripaar.Validator.getControllerViewFields(java.lang.Class):java.util.List");
    }

    private List<Field> getViewFields(Class<?> clazz) {
        List<Field> viewFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (View.class.isAssignableFrom(field.getType())) {
                viewFields.add(field);
            }
        }
        return viewFields;
    }

    private boolean isSaripaarAnnotatedField(Field field, Set<Class<? extends Annotation>> registeredAnnotations) {
        boolean hasOrderAnnotation;
        if (field.getAnnotation(Order.class) != null) {
            hasOrderAnnotation = true;
        } else {
            hasOrderAnnotation = false;
        }
        boolean hasSaripaarAnnotation = false;
        if (!hasOrderAnnotation) {
            for (Annotation annotation : field.getAnnotations()) {
                hasSaripaarAnnotation = registeredAnnotations.contains(annotation.annotationType());
                if (hasSaripaarAnnotation) {
                    break;
                }
            }
        }
        if (hasOrderAnnotation || hasSaripaarAnnotation) {
            return true;
        }
        return false;
    }

    private Map<View, ArrayList<Pair<Rule, ViewDataAdapter>>> createRules(List<Field> annotatedFields) {
        Map<View, ArrayList<Pair<Rule, ViewDataAdapter>>> viewRulesMap = new LinkedHashMap<>();
        for (Field field : annotatedFields) {
            ArrayList<Pair<Rule, ViewDataAdapter>> ruleAdapterPairs = new ArrayList<>();
            for (Annotation fieldAnnotation : field.getAnnotations()) {
                if (isSaripaarAnnotation(fieldAnnotation.annotationType())) {
                    ruleAdapterPairs.add(getRuleAdapterPair(fieldAnnotation, field));
                }
            }
            Collections.sort(ruleAdapterPairs, this.mSequenceComparator);
            viewRulesMap.put(getView(field), ruleAdapterPairs);
        }
        return viewRulesMap;
    }

    private Pair<Rule, ViewDataAdapter> getRuleAdapterPair(Annotation saripaarAnnotation, Field viewField) {
        Class<? extends Annotation> annotationType = saripaarAnnotation.annotationType();
        Class<?> viewFieldType = viewField.getType();
        Class<?> ruleDataType = Reflector.getRuleDataType(saripaarAnnotation);
        ViewDataAdapter dataAdapter = getDataAdapter(annotationType, viewFieldType, ruleDataType);
        if (dataAdapter == null) {
            String viewType = viewFieldType.getName();
            throw new UnsupportedOperationException(String.format("To use '%s' on '%s', register a '%s' that returns a '%s' from the '%s'.", new Object[]{annotationType.getName(), viewType, ViewDataAdapter.class.getName(), ruleDataType.getName(), viewType}));
        }
        if (this.mValidationContext == null) {
            this.mValidationContext = new ValidationContext(getContext(viewField));
        }
        return new Pair<>(Reflector.instantiateRule(getRuleType(saripaarAnnotation), saripaarAnnotation, this.mValidationContext), dataAdapter);
    }

    private ViewDataAdapter getDataAdapter(Class<? extends Annotation> annotationType, Class<?> viewFieldType, Class<?> adapterDataType) {
        ViewDataAdapter dataAdapter = SARIPAAR_REGISTRY.getDataAdapter(annotationType, viewFieldType);
        if (dataAdapter != null) {
            return dataAdapter;
        }
        HashMap<Class<?>, ViewDataAdapter> dataTypeAdapterMap = this.mRegisteredAdaptersMap.get(viewFieldType);
        if (dataTypeAdapterMap != null) {
            return dataTypeAdapterMap.get(adapterDataType);
        }
        return null;
    }

    private Context getContext(Field viewField) {
        try {
            if (!viewField.isAccessible()) {
                viewField.setAccessible(true);
            }
            return ((View) viewField.get(this.mController)).getContext();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Class<? extends AnnotationRule> getRuleType(Annotation ruleAnnotation) {
        ValidateUsing validateUsing = (ValidateUsing) ruleAnnotation.annotationType().getAnnotation(ValidateUsing.class);
        if (validateUsing != null) {
            return validateUsing.value();
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: android.view.View} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.View getView(java.lang.reflect.Field r9) {
        /*
            r8 = this;
            r3 = 0
            r4 = 1
            r9.setAccessible(r4)     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            java.lang.Object r4 = r8.mController     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            java.lang.Object r4 = r9.get(r4)     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            r0 = r4
            android.view.View r0 = (android.view.View) r0     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            r3 = r0
            if (r3 != 0) goto L_0x0036
            java.lang.String r4 = "'%s %s' is null."
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            r6 = 0
            java.lang.Class r7 = r9.getType()     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            java.lang.String r7 = r7.getSimpleName()     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            r5[r6] = r7     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            r6 = 1
            java.lang.String r7 = r9.getName()     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            r5[r6] = r7     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            java.lang.String r2 = java.lang.String.format(r4, r5)     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            r4.<init>(r2)     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
            throw r4     // Catch:{ IllegalArgumentException -> 0x0032, IllegalAccessException -> 0x0037 }
        L_0x0032:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0036:
            return r3
        L_0x0037:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobsandgeeks.saripaar.Validator.getView(java.lang.reflect.Field):android.view.View");
    }

    private void validateUnorderedFieldsWithCallbackTill(View view, boolean async) {
        validateFieldsWithCallbackTill(view, false, (String) null, async);
    }

    private void validateOrderedFieldsWithCallbackTill(View view, String reasonSuffix, boolean async) {
        validateFieldsWithCallbackTill(view, true, reasonSuffix, async);
    }

    private void validateFieldsWithCallbackTill(View view, boolean orderedFields, String reasonSuffix, boolean async) {
        createRulesSafelyAndLazily(false);
        if (async) {
            if (this.mAsyncValidationTask != null) {
                this.mAsyncValidationTask.cancel(true);
            }
            this.mAsyncValidationTask = new AsyncValidationTask(view, orderedFields, reasonSuffix);
            this.mAsyncValidationTask.execute((Void[]) null);
            return;
        }
        triggerValidationListenerCallback(validateTill(view, orderedFields, reasonSuffix));
    }

    /* access modifiers changed from: private */
    public synchronized ValidationReport validateTill(View view, boolean requiresOrderedRules, String reasonSuffix) {
        if (requiresOrderedRules) {
            assertOrderedFields(this.mOrderedFields, reasonSuffix);
        }
        assertNotNull(this.mValidationListener, "validationListener");
        return getValidationReport(view, this.mViewRulesMap, this.mValidationMode);
    }

    /* access modifiers changed from: private */
    public void triggerValidationListenerCallback(ValidationReport validationReport) {
        List<ValidationError> validationErrors = validationReport.errors;
        if (validationErrors.size() != 0 || validationReport.hasMoreErrors) {
            this.mValidationListener.onValidationFailed(validationErrors);
        } else {
            this.mValidationListener.onValidationSucceeded();
        }
    }

    private void assertOrderedFields(boolean orderedRules, String reasonSuffix) {
        if (!orderedRules) {
            throw new IllegalStateException("Rules are unordered, all view fields should be ordered using the '@Order' annotation " + reasonSuffix);
        }
    }

    private ValidationReport getValidationReport(View targetView, Map<View, ArrayList<Pair<Rule, ViewDataAdapter>>> viewRulesMap, Mode validationMode) {
        List<ValidationError> validationErrors = new ArrayList<>();
        Set<View> views = viewRulesMap.keySet();
        boolean addErrorToReport = targetView != null;
        boolean hasMoreErrors = false;
        loop0:
        for (View view : views) {
            ArrayList<Pair<Rule, ViewDataAdapter>> ruleAdapterPairs = viewRulesMap.get(view);
            int nRules = ruleAdapterPairs.size();
            List<Rule> failedRules = null;
            for (int i = 0; i < nRules; i++) {
                if (view.isShown() && view.isEnabled()) {
                    Pair<Rule, ViewDataAdapter> ruleAdapterPair = ruleAdapterPairs.get(i);
                    Rule failedRule = validateViewWithRule(view, (Rule) ruleAdapterPair.first, (ViewDataAdapter) ruleAdapterPair.second);
                    boolean isLastRuleForView = nRules == i + 1;
                    if (failedRule != null) {
                        if (addErrorToReport) {
                            if (failedRules == null) {
                                failedRules = new ArrayList<>();
                                validationErrors.add(new ValidationError(view, failedRules));
                            }
                            failedRules.add(failedRule);
                        } else {
                            hasMoreErrors = true;
                        }
                        if (Mode.IMMEDIATE.equals(validationMode) && isLastRuleForView) {
                            break loop0;
                        }
                    }
                    if (view.equals(targetView) && isLastRuleForView) {
                        addErrorToReport = false;
                    }
                }
            }
            if (((failedRules == null || failedRules.size() == 0) && !hasMoreErrors) && this.mViewValidatedAction != null) {
                triggerViewValidatedCallback(this.mViewValidatedAction, view);
            }
        }
        return new ValidationReport(validationErrors, hasMoreErrors);
    }

    private Rule validateViewWithRule(View view, Rule rule, ViewDataAdapter dataAdapter) {
        boolean valid = false;
        if (rule instanceof AnnotationRule) {
            try {
                valid = rule.isValid(dataAdapter.getData(view));
            } catch (ConversionException e) {
                valid = false;
                e.printStackTrace();
            }
        } else if (rule instanceof QuickRule) {
            valid = rule.isValid(view);
        }
        if (valid) {
            return null;
        }
        return rule;
    }

    private void triggerViewValidatedCallback(final ViewValidatedAction viewValidatedAction, final View view) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            viewValidatedAction.onAllRulesPassed(view);
        } else {
            runOnMainThread(new Runnable() {
                public void run() {
                    viewValidatedAction.onAllRulesPassed(view);
                }
            });
        }
    }

    private void runOnMainThread(Runnable runnable) {
        if (this.mViewValidatedActionHandler == null) {
            this.mViewValidatedActionHandler = new Handler(Looper.getMainLooper());
        }
        this.mViewValidatedActionHandler.post(runnable);
    }

    private View getLastView() {
        View lastView = null;
        Iterator<View> it = this.mViewRulesMap.keySet().iterator();
        while (it.hasNext()) {
            lastView = it.next();
        }
        return lastView;
    }

    private View getViewBefore(View view) {
        ArrayList<View> views = new ArrayList<>(this.mViewRulesMap.keySet());
        int nViews = views.size();
        int i = 0;
        while (i < nViews) {
            if (views.get(i) != view) {
                i++;
            } else if (i > 0) {
                return views.get(i - 1);
            } else {
                return null;
            }
        }
        return null;
    }

    static class ValidationReport {
        List<ValidationError> errors;
        boolean hasMoreErrors;

        ValidationReport(List<ValidationError> errors2, boolean hasMoreErrors2) {
            this.errors = errors2;
            this.hasMoreErrors = hasMoreErrors2;
        }
    }

    class AsyncValidationTask extends AsyncTask<Void, Void, ValidationReport> {
        private boolean mOrderedRules;
        private String mReasonSuffix;
        private View mView;

        public AsyncValidationTask(View view, boolean orderedRules, String reasonSuffix) {
            this.mView = view;
            this.mOrderedRules = orderedRules;
            this.mReasonSuffix = reasonSuffix;
        }

        /* access modifiers changed from: protected */
        public ValidationReport doInBackground(Void... params) {
            return Validator.this.validateTill(this.mView, this.mOrderedRules, this.mReasonSuffix);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(ValidationReport validationReport) {
            Validator.this.triggerValidationListenerCallback(validationReport);
        }
    }
}
