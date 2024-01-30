package com.ua.sdk.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.ua.sdk.ImageUrl;

public class ImageUrlImpl implements ImageUrl, Parcelable {
    public static Parcelable.Creator<ImageUrlImpl> CREATOR = new Parcelable.Creator<ImageUrlImpl>() {
        public ImageUrlImpl createFromParcel(Parcel source) {
            return new ImageUrlImpl(source);
        }

        public ImageUrlImpl[] newArray(int size) {
            return new ImageUrlImpl[size];
        }
    };
    private static final int HEIGHT_LARGE = 600;
    private static final int HEIGHT_MEDIUM = 250;
    private static final int HEIGHT_SMALL = 100;
    private static final int WIDTH_LARGE = 600;
    private static final int WIDTH_MEDIUM = 250;
    private static final int WIDTH_SMALL = 100;
    /* access modifiers changed from: private */
    public String large;
    /* access modifiers changed from: private */
    public String medium;
    /* access modifiers changed from: private */
    public String small;
    /* access modifiers changed from: private */
    public String template;

    public ImageUrlImpl() {
    }

    public String getSmall() {
        if (this.small == null) {
            this.small = getCustom(100, 100);
        }
        return this.small;
    }

    public void setSmall(String small2) {
        this.small = small2;
    }

    public String getMedium() {
        if (this.medium == null) {
            this.medium = getCustom(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        }
        return this.medium;
    }

    public void setMedium(String medium2) {
        this.medium = medium2;
    }

    public String getLarge() {
        if (this.large == null) {
            this.large = getCustom(600, 600);
        }
        return this.large;
    }

    public void setLarge(String large2) {
        this.large = large2;
    }

    public void setTemplate(String template2) {
        this.template = template2;
    }

    public String getTemplate() {
        return this.template;
    }

    public String getCustom(int width, int height) {
        if (this.template != null) {
            return new ImageUriBuilder(this.template).setWidth(width).setHeight(height).getHref();
        }
        if (this.large != null) {
            return this.large;
        }
        if (this.medium != null) {
            return this.medium;
        }
        if (this.small != null) {
            return this.small;
        }
        return null;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String large;
        private String medium;
        private String small;
        private String template;

        public Builder setSmall(String small2) {
            this.small = small2;
            return this;
        }

        public Builder setMedium(String medium2) {
            this.medium = medium2;
            return this;
        }

        public Builder setLarge(String large2) {
            this.large = large2;
            return this;
        }

        public Builder setTemplate(String template2) {
            this.template = template2;
            return this;
        }

        public Builder setUri(String uri) {
            this.small = uri;
            this.medium = uri;
            this.large = uri;
            return this;
        }

        public ImageUrlImpl build() {
            ImageUrlImpl answer = new ImageUrlImpl();
            String unused = answer.small = this.small;
            String unused2 = answer.medium = this.medium;
            String unused3 = answer.large = this.large;
            String unused4 = answer.template = this.template;
            return answer;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.small);
        dest.writeString(this.medium);
        dest.writeString(this.large);
        dest.writeString(this.template);
    }

    private ImageUrlImpl(Parcel in) {
        this.small = in.readString();
        this.medium = in.readString();
        this.large = in.readString();
        this.template = in.readString();
    }

    class ImageUriBuilder extends BaseReferenceBuilder {
        public ImageUriBuilder(String hrefTemplate) {
            super(hrefTemplate);
        }

        public ImageUriBuilder setWidth(int width) {
            setParam("width_px", width);
            return this;
        }

        public ImageUriBuilder setHeight(int height) {
            setParam("height_px", height);
            return this;
        }
    }
}
