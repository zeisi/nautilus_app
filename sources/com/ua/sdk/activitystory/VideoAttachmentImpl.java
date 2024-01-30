package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.activitystory.Attachment;
import com.ua.sdk.activitystory.VideoAttachment;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.ImageUrlImpl;
import java.util.Date;

public class VideoAttachmentImpl implements VideoAttachment {
    public static final Parcelable.Creator<VideoAttachmentImpl> CREATOR = new Parcelable.Creator<VideoAttachmentImpl>() {
        public VideoAttachmentImpl createFromParcel(Parcel source) {
            return new VideoAttachmentImpl(source);
        }

        public VideoAttachmentImpl[] newArray(int size) {
            return new VideoAttachmentImpl[size];
        }
    };
    @SerializedName("object")
    Data data;
    transient ImageUrl imageUrl;
    transient VideoAttachment.Provider provider;

    public VideoAttachmentImpl() {
    }

    public VideoAttachmentImpl(Attachment.Type type) {
        this.data = new Data();
        this.data.type = type;
    }

    public String getUri() {
        return this.data.uri;
    }

    public String getProviderId() {
        return this.data.providerId;
    }

    public ImageUrl getThumbnailUrl() {
        if (this.imageUrl == null) {
            this.imageUrl = ImageUrlImpl.getBuilder().setUri(this.data.thumbnailUri).setTemplate(this.data.thumbnailUriTemplate).build();
        }
        return this.imageUrl;
    }

    public VideoAttachment.Provider getProvider() {
        if (this.provider == null) {
            if (this.data.providerId == null) {
                this.provider = VideoAttachment.Provider.UNKNOWN;
            } else if (this.data.providerId.equalsIgnoreCase(VideoAttachment.Provider.OOYALA.name())) {
                this.provider = VideoAttachment.Provider.OOYALA;
            } else {
                this.provider = VideoAttachment.Provider.UNKNOWN;
            }
        }
        return this.provider;
    }

    public String getProviderString() {
        return this.data.provider;
    }

    public Attachment.Type getType() {
        return this.data.type;
    }

    public Attachment.Status getStatus() {
        return this.data.status;
    }

    public Date getPublished() {
        return this.data.published;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    private VideoAttachmentImpl(Parcel in) {
        this.data = (Data) in.readParcelable(Data.class.getClassLoader());
    }

    public static class Data implements Parcelable {
        public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
        @SerializedName("provider")
        String provider;
        @SerializedName("provider_id")
        String providerId;
        @SerializedName("published")
        Date published;
        @SerializedName("status")
        Attachment.Status status;
        @SerializedName("thumbnail_uri")
        String thumbnailUri;
        @SerializedName("thumbnail_uri_template")
        String thumbnailUriTemplate;
        @SerializedName("type")
        Attachment.Type type;
        @SerializedName("uri")
        String uri;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            int i = -1;
            dest.writeString(this.uri);
            dest.writeInt(this.type == null ? -1 : this.type.ordinal());
            dest.writeLong(this.published != null ? this.published.getTime() : -1);
            dest.writeString(this.provider);
            dest.writeString(this.providerId);
            dest.writeString(this.thumbnailUri);
            dest.writeString(this.thumbnailUriTemplate);
            if (this.status != null) {
                i = this.status.ordinal();
            }
            dest.writeInt(i);
        }

        public Data() {
        }

        private Data(Parcel in) {
            Attachment.Status status2 = null;
            this.uri = in.readString();
            int tmpType = in.readInt();
            this.type = tmpType == -1 ? null : Attachment.Type.values()[tmpType];
            long tmpPublished = in.readLong();
            this.published = tmpPublished == -1 ? null : new Date(tmpPublished);
            this.provider = in.readString();
            this.providerId = in.readString();
            this.thumbnailUri = in.readString();
            this.thumbnailUriTemplate = in.readString();
            int tmpStatus = in.readInt();
            this.status = tmpStatus != -1 ? Attachment.Status.values()[tmpStatus] : status2;
        }
    }

    static class ImageUriBuilder extends BaseReferenceBuilder {
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
