package com.ua.sdk.authentication;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.LinkEntityRef;
import java.util.Date;

public class FilemobileCredentialImpl extends ApiTransferObject implements FilemobileCredential, Parcelable {
    public static Parcelable.Creator<FilemobileCredentialImpl> CREATOR = new Parcelable.Creator<FilemobileCredentialImpl>() {
        public FilemobileCredentialImpl createFromParcel(Parcel source) {
            return new FilemobileCredentialImpl(source);
        }

        public FilemobileCredentialImpl[] newArray(int size) {
            return new FilemobileCredentialImpl[size];
        }
    };
    private Date created;
    private String token;
    private String uid;
    private Uri uploaderUri;
    private String vhost;

    public FilemobileCredentialImpl() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public String getVhost() {
        return this.vhost;
    }

    public void setVhost(String vhost2) {
        this.vhost = vhost2;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid2) {
        this.uid = uid2;
    }

    public Uri getUploaderUri() {
        return this.uploaderUri;
    }

    public void setUploaderUri(Uri uploaderUri2) {
        this.uploaderUri = uploaderUri2;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created2) {
        this.created = created2;
    }

    public EntityRef getRef() {
        return new LinkEntityRef("", getLocalId(), getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.vhost);
        dest.writeString(this.uid);
        dest.writeParcelable(this.uploaderUri, flags);
        dest.writeLong(this.created != null ? this.created.getTime() : -1);
    }

    private FilemobileCredentialImpl(Parcel in) {
        super(in);
        this.token = in.readString();
        this.vhost = in.readString();
        this.uid = in.readString();
        this.uploaderUri = (Uri) in.readParcelable(Uri.class.getClassLoader());
        long tempCreated = in.readLong();
        this.created = tempCreated == -1 ? null : new Date(tempCreated);
    }
}
