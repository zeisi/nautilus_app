package uk.co.chrisjenx.calligraphy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

class CalligraphyFactory {
    private static final String ACTION_BAR_SUBTITLE = "action_bar_subtitle";
    private static final String ACTION_BAR_TITLE = "action_bar_title";
    private final int mAttributeId;

    protected static int[] getStyleForTextView(TextView view) {
        int[] styleIds = {-1, -1};
        if (isActionBarTitle(view)) {
            styleIds[0] = 16843470;
            styleIds[1] = 16843512;
        } else if (isActionBarSubTitle(view)) {
            styleIds[0] = 16843470;
            styleIds[1] = 16843513;
        }
        if (styleIds[0] == -1) {
            styleIds[0] = CalligraphyConfig.get().getClassStyles().containsKey(view.getClass()) ? CalligraphyConfig.get().getClassStyles().get(view.getClass()).intValue() : 16842804;
        }
        return styleIds;
    }

    @SuppressLint({"NewApi"})
    protected static boolean isActionBarTitle(TextView view) {
        if (matchesResourceIdName(view, ACTION_BAR_TITLE)) {
            return true;
        }
        if (parentIsToolbarV7(view)) {
            return TextUtils.equals(((Toolbar) view.getParent()).getTitle(), view.getText());
        }
        return false;
    }

    @SuppressLint({"NewApi"})
    protected static boolean isActionBarSubTitle(TextView view) {
        if (matchesResourceIdName(view, ACTION_BAR_SUBTITLE)) {
            return true;
        }
        if (parentIsToolbarV7(view)) {
            return TextUtils.equals(((Toolbar) view.getParent()).getSubtitle(), view.getText());
        }
        return false;
    }

    protected static boolean parentIsToolbarV7(View view) {
        return CalligraphyUtils.canCheckForV7Toolbar() && view.getParent() != null && (view.getParent() instanceof Toolbar);
    }

    protected static boolean matchesResourceIdName(View view, String matches) {
        if (view.getId() == -1) {
            return false;
        }
        return view.getResources().getResourceEntryName(view.getId()).equalsIgnoreCase(matches);
    }

    public CalligraphyFactory(int attributeId) {
        this.mAttributeId = attributeId;
    }

    public View onViewCreated(View view, Context context, AttributeSet attrs) {
        if (!(view == null || view.getTag(R.id.calligraphy_tag_id) == Boolean.TRUE)) {
            onViewCreatedInternal(view, context, attrs);
            view.setTag(R.id.calligraphy_tag_id, Boolean.TRUE);
        }
        return view;
    }

    /* access modifiers changed from: package-private */
    public void onViewCreatedInternal(View view, final Context context, AttributeSet attrs) {
        boolean deferred = false;
        if (view instanceof TextView) {
            if (!TypefaceUtils.isLoaded(((TextView) view).getTypeface())) {
                String textViewFont = CalligraphyUtils.pullFontPathFromView(context, attrs, this.mAttributeId);
                if (TextUtils.isEmpty(textViewFont)) {
                    textViewFont = CalligraphyUtils.pullFontPathFromStyle(context, attrs, this.mAttributeId);
                }
                if (TextUtils.isEmpty(textViewFont)) {
                    textViewFont = CalligraphyUtils.pullFontPathFromTextAppearance(context, attrs, this.mAttributeId);
                }
                if (TextUtils.isEmpty(textViewFont)) {
                    int[] styleForTextView = getStyleForTextView((TextView) view);
                    if (styleForTextView[1] != -1) {
                        textViewFont = CalligraphyUtils.pullFontPathFromTheme(context, styleForTextView[0], styleForTextView[1], this.mAttributeId);
                    } else {
                        textViewFont = CalligraphyUtils.pullFontPathFromTheme(context, styleForTextView[0], this.mAttributeId);
                    }
                }
                if (matchesResourceIdName(view, ACTION_BAR_TITLE) || matchesResourceIdName(view, ACTION_BAR_SUBTITLE)) {
                    deferred = true;
                }
                CalligraphyUtils.applyFontToTextView(context, (TextView) view, CalligraphyConfig.get(), textViewFont, deferred);
            } else {
                return;
            }
        }
        if (CalligraphyUtils.canCheckForV7Toolbar() && (view instanceof Toolbar)) {
            final ViewGroup parent = (ViewGroup) view;
            parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @TargetApi(16)
                public void onGlobalLayout() {
                    int childCount = parent.getChildCount();
                    if (childCount != 0) {
                        for (int i = 0; i < childCount; i++) {
                            CalligraphyFactory.this.onViewCreated(parent.getChildAt(i), context, (AttributeSet) null);
                        }
                    }
                    if (Build.VERSION.SDK_INT < 16) {
                        parent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }
}
