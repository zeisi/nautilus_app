package uk.co.chrisjenx.calligraphy;

import android.os.Build;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CalligraphyConfig {
    private static final Map<Class<? extends TextView>, Integer> DEFAULT_STYLES = new HashMap();
    private static CalligraphyConfig sInstance;
    private final int mAttrId;
    private final Map<Class<? extends TextView>, Integer> mClassStyleAttributeMap;
    private final boolean mCustomViewCreation;
    private final String mFontPath;
    private final boolean mIsFontSet;
    private final boolean mReflection;

    static {
        DEFAULT_STYLES.put(TextView.class, 16842884);
        DEFAULT_STYLES.put(Button.class, 16842824);
        DEFAULT_STYLES.put(EditText.class, 16842862);
        DEFAULT_STYLES.put(AutoCompleteTextView.class, 16842859);
        DEFAULT_STYLES.put(MultiAutoCompleteTextView.class, 16842859);
        DEFAULT_STYLES.put(CheckBox.class, 16842860);
        DEFAULT_STYLES.put(RadioButton.class, 16842878);
        DEFAULT_STYLES.put(ToggleButton.class, 16842827);
    }

    public static void initDefault(CalligraphyConfig calligraphyConfig) {
        sInstance = calligraphyConfig;
    }

    public static CalligraphyConfig get() {
        if (sInstance == null) {
            sInstance = new CalligraphyConfig(new Builder());
        }
        return sInstance;
    }

    protected CalligraphyConfig(Builder builder) {
        this.mIsFontSet = builder.isFontSet;
        this.mFontPath = builder.fontAssetPath;
        this.mAttrId = builder.attrId;
        this.mReflection = builder.reflection;
        this.mCustomViewCreation = builder.customViewCreation;
        Map<Class<? extends TextView>, Integer> tempMap = new HashMap<>(DEFAULT_STYLES);
        tempMap.putAll(builder.mStyleClassMap);
        this.mClassStyleAttributeMap = Collections.unmodifiableMap(tempMap);
    }

    public String getFontPath() {
        return this.mFontPath;
    }

    /* access modifiers changed from: package-private */
    public boolean isFontSet() {
        return this.mIsFontSet;
    }

    public boolean isReflection() {
        return this.mReflection;
    }

    public boolean isCustomViewCreation() {
        return this.mCustomViewCreation;
    }

    /* access modifiers changed from: package-private */
    public Map<Class<? extends TextView>, Integer> getClassStyles() {
        return this.mClassStyleAttributeMap;
    }

    public int getAttrId() {
        return this.mAttrId;
    }

    public static class Builder {
        public static final int INVALID_ATTR_ID = -1;
        /* access modifiers changed from: private */
        public int attrId;
        /* access modifiers changed from: private */
        public boolean customViewCreation;
        /* access modifiers changed from: private */
        public String fontAssetPath;
        /* access modifiers changed from: private */
        public boolean isFontSet;
        /* access modifiers changed from: private */
        public Map<Class<? extends TextView>, Integer> mStyleClassMap;
        /* access modifiers changed from: private */
        public boolean reflection;

        public Builder() {
            this.reflection = Build.VERSION.SDK_INT >= 11;
            this.customViewCreation = true;
            this.attrId = R.attr.fontPath;
            this.isFontSet = false;
            this.fontAssetPath = null;
            this.mStyleClassMap = new HashMap();
        }

        public Builder setFontAttrId(int fontAssetAttrId) {
            if (fontAssetAttrId == -1) {
                fontAssetAttrId = -1;
            }
            this.attrId = fontAssetAttrId;
            return this;
        }

        public Builder setDefaultFontPath(String defaultFontAssetPath) {
            this.isFontSet = !TextUtils.isEmpty(defaultFontAssetPath);
            this.fontAssetPath = defaultFontAssetPath;
            return this;
        }

        public Builder disablePrivateFactoryInjection() {
            this.reflection = false;
            return this;
        }

        public Builder disableCustomViewInflation() {
            this.customViewCreation = false;
            return this;
        }

        public Builder addCustomStyle(Class<? extends TextView> styleClass, int styleResourceAttribute) {
            if (!(styleClass == null || styleResourceAttribute == 0)) {
                this.mStyleClassMap.put(styleClass, Integer.valueOf(styleResourceAttribute));
            }
            return this;
        }

        public CalligraphyConfig build() {
            this.isFontSet = !TextUtils.isEmpty(this.fontAssetPath);
            return new CalligraphyConfig(this);
        }
    }
}
