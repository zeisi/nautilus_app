package com.ua.sdk.activitystory;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import com.nautilus.omni.bleservices.BLEScanManager;
import com.ua.sdk.Convert;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.MeasurementSystem;
import com.ua.sdk.internal.ImageUrlImpl;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActivityStoryTemplateImpl implements ActivityStoryTemplate, Parcelable {
    public static Parcelable.Creator<ActivityStoryTemplateImpl> CREATOR = new Parcelable.Creator<ActivityStoryTemplateImpl>() {
        public ActivityStoryTemplateImpl createFromParcel(Parcel source) {
            return new ActivityStoryTemplateImpl(source);
        }

        public ActivityStoryTemplateImpl[] newArray(int size) {
            return new ActivityStoryTemplateImpl[size];
        }
    };
    @SerializedName("icon")
    String mIcon;
    transient Map<String, Object> mMessageArgs;
    @SerializedName("message")
    String mMessageTemplate;
    transient Map<String, Object> mSubtitleArgs;
    @SerializedName("subtitle")
    String mSubtitleTemplate;
    transient Map<String, Object> mTitleArgs;
    @SerializedName("title")
    String mTitleTemplate;

    public ImageUrl getIconUrl() {
        if (this.mIcon == null) {
            return null;
        }
        return ImageUrlImpl.getBuilder().setUri(this.mIcon).build();
    }

    public String getTitle(MeasurementSystem ms) {
        return createString(this.mTitleTemplate, this.mTitleArgs, ms);
    }

    public String getTitleTemplate() {
        return this.mTitleTemplate;
    }

    public Map<String, Object> getTitleArgs() {
        return this.mTitleArgs;
    }

    public String getSubtitle(MeasurementSystem ms) {
        return createString(this.mSubtitleTemplate, this.mSubtitleArgs, ms);
    }

    public String getSubtitleTemplate() {
        return this.mSubtitleTemplate;
    }

    public Map<String, Object> getSubtitleArgs() {
        return this.mSubtitleArgs;
    }

    public String getMessage(MeasurementSystem ms) {
        return createString(this.mMessageTemplate, this.mMessageArgs, ms);
    }

    public String getMessageTemplate() {
        return this.mMessageTemplate;
    }

    public Map<String, Object> getMessageArgs() {
        return this.mMessageArgs;
    }

    public void fillTemplateArgs(JsonObject json) {
        this.mTitleArgs = getArgs(this.mTitleTemplate, json);
        this.mSubtitleArgs = getArgs(this.mSubtitleTemplate, json);
        this.mMessageArgs = getArgs(this.mMessageTemplate, json);
    }

    /* access modifiers changed from: package-private */
    public Map<String, Object> getArgs(String template, JsonObject json) {
        Object asString;
        String subkey;
        if (json == null || template == null || template.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<String, Object> args = null;
        boolean escaped = false;
        int openBrackets = 0;
        int closeBrackets = 0;
        int open = -1;
        int length = template.length();
        for (int i = 0; i < length; i++) {
            char c = template.charAt(i);
            if (!escaped) {
                switch (c) {
                    case '\\':
                        escaped = true;
                        break;
                    case '{':
                        openBrackets++;
                        if (openBrackets != 1) {
                            break;
                        } else {
                            open = i;
                            break;
                        }
                    case BLEScanManager.CONVERT_TO_SIGNAL_STRENGTH:
                        if (openBrackets > 0 && openBrackets == (closeBrackets = closeBrackets + 1)) {
                            JsonElement e = json;
                            String key = template.substring(open + openBrackets, (i + 1) - closeBrackets);
                            while (key.length() > 0) {
                                int periodIndex = key.indexOf(46);
                                if (periodIndex > 0) {
                                    subkey = key.substring(0, periodIndex);
                                    key = key.substring(periodIndex + 1);
                                } else {
                                    subkey = key;
                                    key = "";
                                }
                                if (e.isJsonObject()) {
                                    e = e.getAsJsonObject().get(subkey);
                                } else {
                                    e = null;
                                }
                            }
                            if (e != null && !e.isJsonNull()) {
                                String fullKey = template.substring(open, i + 1);
                                if (e.isJsonPrimitive()) {
                                    JsonPrimitive primitive = (JsonPrimitive) e;
                                    if (primitive.isBoolean()) {
                                        asString = Boolean.valueOf(primitive.getAsBoolean());
                                    } else if (primitive.isNumber()) {
                                        asString = primitive.getAsNumber();
                                    } else {
                                        asString = primitive.getAsString();
                                    }
                                } else {
                                    asString = e.getAsString();
                                }
                                if (args == null) {
                                    args = new HashMap<>(2);
                                }
                                args.put(fullKey, asString);
                            }
                            open = -1;
                            openBrackets = 0;
                            closeBrackets = 0;
                            break;
                        }
                }
            } else {
                escaped = false;
            }
        }
        return args == null ? Collections.emptyMap() : args;
    }

    private String createString(String template, Map<String, Object> args, MeasurementSystem ms) {
        if (template == null || template.isEmpty()) {
            return "";
        }
        if (ms == null) {
            ms = MeasurementSystem.IMPERIAL;
        }
        StringBuilder out = new StringBuilder(template.length());
        boolean escaped = false;
        int openBrackets = 0;
        int closeBrackets = 0;
        int open = -1;
        int length = template.length();
        for (int i = 0; i < length; i++) {
            char c = template.charAt(i);
            if (!escaped) {
                switch (c) {
                    case '\\':
                        escaped = true;
                        out.append(c);
                        break;
                    case '{':
                        openBrackets++;
                        if (openBrackets != 1) {
                            break;
                        } else {
                            open = i;
                            break;
                        }
                    case BLEScanManager.CONVERT_TO_SIGNAL_STRENGTH:
                        if (openBrackets > 0 && openBrackets == (closeBrackets = closeBrackets + 1)) {
                            String key = template.substring(open, i + 1);
                            Object value = args.get(key);
                            if (value == null) {
                                out.append(key);
                            } else if (key.contains("distance")) {
                                writeDistance(out, value, ms);
                            } else {
                                out.append(value);
                            }
                            open = -1;
                            openBrackets = 0;
                            closeBrackets = 0;
                            break;
                        }
                    default:
                        if (open >= 0) {
                            break;
                        } else {
                            out.append(c);
                            break;
                        }
                }
            } else {
                escaped = false;
                out.append(c);
            }
        }
        return out.toString();
    }

    private void writeDistance(StringBuilder out, Object distance, MeasurementSystem ms) {
        double meters;
        if (distance instanceof Number) {
            meters = ((Number) distance).doubleValue();
        } else {
            try {
                meters = Double.parseDouble(distance.toString());
            } catch (NumberFormatException e) {
                out.append(distance.toString());
                return;
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        switch (ms) {
            case IMPERIAL:
                out.append(df.format(Convert.meterToMile(Double.valueOf(meters)).doubleValue()));
                out.append("mi");
                return;
            case METRIC:
                out.append(df.format(Convert.meterToKilometer(Double.valueOf(meters)).doubleValue()));
                out.append("km");
                return;
            default:
                return;
        }
    }

    public int describeContents() {
        return 0;
    }

    private void writeArgsToParcel(Map<String, Object> args, Parcel dest) {
        if (args == null || args.isEmpty()) {
            dest.writeBundle((Bundle) null);
            return;
        }
        Bundle bundle = new Bundle();
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            bundle.putSerializable(entry.getKey(), (Serializable) entry.getValue());
        }
        dest.writeBundle(bundle);
    }

    private Map<String, Object> readArgsFromParcel(Parcel in) {
        Bundle bundle = in.readBundle();
        if (bundle == null) {
            return Collections.emptyMap();
        }
        HashMap<String, Object> args = new HashMap<>(bundle.size());
        for (String key : bundle.keySet()) {
            args.put(key, bundle.getSerializable(key));
        }
        return args;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mIcon);
        writeArgsToParcel(this.mTitleArgs, dest);
        writeArgsToParcel(this.mSubtitleArgs, dest);
        writeArgsToParcel(this.mMessageArgs, dest);
        dest.writeString(this.mTitleTemplate);
        dest.writeString(this.mSubtitleTemplate);
        dest.writeString(this.mMessageTemplate);
    }

    public ActivityStoryTemplateImpl() {
    }

    private ActivityStoryTemplateImpl(Parcel in) {
        this.mIcon = in.readString();
        this.mTitleArgs = readArgsFromParcel(in);
        this.mSubtitleArgs = readArgsFromParcel(in);
        this.mMessageArgs = readArgsFromParcel(in);
        this.mTitleTemplate = in.readString();
        this.mSubtitleTemplate = in.readString();
        this.mMessageTemplate = in.readString();
    }

    public String toString() {
        return toString((MeasurementSystem) null);
    }

    public String toString(MeasurementSystem ms) {
        StringBuilder out = new StringBuilder();
        if (this.mTitleTemplate != null) {
            out.append(getTitle(ms));
        }
        if (this.mSubtitleTemplate != null) {
            out.append(10);
            out.append(getSubtitle(ms));
        }
        if (this.mMessageTemplate != null) {
            out.append(10);
            out.append(getMessage(ms));
        }
        return out.toString();
    }
}
