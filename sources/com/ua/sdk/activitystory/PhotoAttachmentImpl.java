package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.activitystory.Attachment;
import com.ua.sdk.internal.ImageUrlImpl;
import java.util.Date;

public class PhotoAttachmentImpl implements PhotoAttachment {
    public static final Parcelable.Creator<PhotoAttachmentImpl> CREATOR = new Parcelable.Creator<PhotoAttachmentImpl>() {
        public PhotoAttachmentImpl createFromParcel(Parcel source) {
            return new PhotoAttachmentImpl(source);
        }

        public PhotoAttachmentImpl[] newArray(int size) {
            return new PhotoAttachmentImpl[size];
        }
    };
    @SerializedName("object")
    Data data;

    public PhotoAttachmentImpl() {
    }

    public PhotoAttachmentImpl(Attachment.Type type) {
        this.data = new Data();
        this.data.type = type;
    }

    public String getUri() {
        return this.data.uri;
    }

    public ImageUrl getImageUrl() {
        if (this.data.template == null && this.data.uri == null) {
            return null;
        }
        return ImageUrlImpl.getBuilder().setUri(this.data.uri).setTemplate(this.data.template).build();
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

    private PhotoAttachmentImpl(Parcel in) {
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
        @SerializedName("published")
        Date published;
        @SerializedName("status")
        Attachment.Status status;
        @SerializedName("template")
        String template;
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
            dest.writeString(this.template);
            dest.writeInt(this.type == null ? -1 : this.type.ordinal());
            dest.writeLong(this.published != null ? this.published.getTime() : -1);
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
            this.template = in.readString();
            int tmpType = in.readInt();
            this.type = tmpType == -1 ? null : Attachment.Type.values()[tmpType];
            long tmpPublished = in.readLong();
            this.published = tmpPublished == -1 ? null : new Date(tmpPublished);
            int tmpStatus = in.readInt();
            this.status = tmpStatus != -1 ? Attachment.Status.values()[tmpStatus] : status2;
        }
    }
}
