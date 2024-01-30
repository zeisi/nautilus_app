package com.ua.sdk.page;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class PageSettingImpl implements PageSetting {
    public static Parcelable.Creator<PageSettingImpl> CREATOR = new Parcelable.Creator<PageSettingImpl>() {
        public PageSettingImpl createFromParcel(Parcel source) {
            return new PageSettingImpl(source);
        }

        public PageSettingImpl[] newArray(int size) {
            return new PageSettingImpl[size];
        }
    };
    @SerializedName("cta_link")
    String ctaLink;
    @SerializedName("cta_target")
    String ctaTarget;
    @SerializedName("cta_text")
    String ctaText;
    @SerializedName("featured_gallery_enabled")
    Boolean featuredGalleryEnabled;
    @SerializedName("qs_graph_enabled")
    Boolean qsGraphEnabled;
    @SerializedName("template")
    String template;

    public PageSettingImpl() {
    }

    public PageSettingImpl(Boolean featuredGalleryEnabled2, Boolean qsGraphEnabled2, String ctaText2, String ctaLink2, String ctaTarget2, String template2) {
        this.featuredGalleryEnabled = featuredGalleryEnabled2;
        this.qsGraphEnabled = qsGraphEnabled2;
        this.ctaText = ctaText2;
        this.ctaLink = ctaLink2;
        this.ctaTarget = ctaTarget2;
        this.template = template2;
    }

    public boolean isFeaturedGalleryEnabled() {
        if (this.featuredGalleryEnabled == null) {
            return false;
        }
        return this.featuredGalleryEnabled.booleanValue();
    }

    public Boolean getFeaturedGalleryEnabled() {
        return this.featuredGalleryEnabled;
    }

    public void setFeaturedGalleryEnabled(Boolean enabled) {
        this.featuredGalleryEnabled = enabled;
    }

    public boolean isQsGraphEnabled() {
        if (this.qsGraphEnabled == null) {
            return false;
        }
        return this.qsGraphEnabled.booleanValue();
    }

    public Boolean getQsGraphEnabled() {
        return this.featuredGalleryEnabled;
    }

    public void setQsGraphEnabled(Boolean enabled) {
        this.qsGraphEnabled = enabled;
    }

    public String getCtaText() {
        return this.ctaText;
    }

    public void setCtaText(String ctaText2) {
        this.ctaText = ctaText2;
    }

    public String getCtaLink() {
        return this.ctaLink;
    }

    public void setCtaLink(String ctaLink2) {
        this.ctaLink = ctaLink2;
    }

    public String getCtaTarget() {
        return this.ctaTarget;
    }

    public void setCtaTarget(String ctaTarget2) {
        this.ctaTarget = ctaTarget2;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template2) {
        this.template = template2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.featuredGalleryEnabled);
        dest.writeValue(this.qsGraphEnabled);
        dest.writeString(this.ctaText);
        dest.writeString(this.ctaLink);
        dest.writeString(this.ctaTarget);
        dest.writeString(this.template);
    }

    private PageSettingImpl(Parcel in) {
        this.featuredGalleryEnabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.qsGraphEnabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ctaText = in.readString();
        this.ctaLink = in.readString();
        this.ctaTarget = in.readString();
        this.template = in.readString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageSettingImpl)) {
            return false;
        }
        PageSettingImpl pageSetting = (PageSettingImpl) o;
        if (this.featuredGalleryEnabled == null ? pageSetting.featuredGalleryEnabled != null : !this.featuredGalleryEnabled.equals(pageSetting.featuredGalleryEnabled)) {
            return false;
        }
        if (this.qsGraphEnabled == null ? pageSetting.qsGraphEnabled != null : !this.qsGraphEnabled.equals(pageSetting.qsGraphEnabled)) {
            return false;
        }
        if (this.ctaText == null ? pageSetting.ctaText != null : !this.ctaText.equals(pageSetting.ctaText)) {
            return false;
        }
        if (this.ctaLink == null ? pageSetting.ctaLink != null : !this.ctaLink.equals(pageSetting.ctaLink)) {
            return false;
        }
        if (this.ctaTarget == null ? pageSetting.ctaTarget != null : !this.ctaTarget.equals(pageSetting.ctaTarget)) {
            return false;
        }
        if (this.template != null) {
            if (this.template.equals(pageSetting.template)) {
                return true;
            }
        } else if (pageSetting.template == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        if (this.featuredGalleryEnabled != null) {
            result = this.featuredGalleryEnabled.hashCode();
        } else {
            result = 0;
        }
        int i6 = result * 31;
        if (this.qsGraphEnabled != null) {
            i = this.qsGraphEnabled.hashCode();
        } else {
            i = 0;
        }
        int i7 = (i6 + i) * 31;
        if (this.ctaText != null) {
            i2 = this.ctaText.hashCode();
        } else {
            i2 = 0;
        }
        int i8 = (i7 + i2) * 31;
        if (this.ctaLink != null) {
            i3 = this.ctaLink.hashCode();
        } else {
            i3 = 0;
        }
        int i9 = (i8 + i3) * 31;
        if (this.ctaTarget != null) {
            i4 = this.ctaTarget.hashCode();
        } else {
            i4 = 0;
        }
        int i10 = (i9 + i4) * 31;
        if (this.template != null) {
            i5 = this.template.hashCode();
        }
        return i10 + i5;
    }
}
