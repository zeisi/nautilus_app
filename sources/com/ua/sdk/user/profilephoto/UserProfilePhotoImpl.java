package com.ua.sdk.user.profilephoto;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.ImageUrlImpl;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import java.util.List;

public class UserProfilePhotoImpl extends ApiTransferObject implements UserProfilePhoto, Parcelable {
    public static Parcelable.Creator<UserProfilePhotoImpl> CREATOR = new Parcelable.Creator<UserProfilePhotoImpl>() {
        public UserProfilePhotoImpl createFromParcel(Parcel source) {
            return new UserProfilePhotoImpl(source);
        }

        public UserProfilePhotoImpl[] newArray(int size) {
            return new UserProfilePhotoImpl[size];
        }
    };
    public static final String REF_LARGE = "large";
    public static final String REF_MEDIUM = "medium";
    public static final String REF_SMALL = "small";
    private String mLargeProfilePhotoURL;
    private String mMediumProfilePhotoURL;
    private String mSmallProfilePhotoURL;
    private transient EntityRef<UserProfilePhoto> ref;

    public UserProfilePhotoImpl() {
    }

    public EntityRef<UserProfilePhoto> getRef() {
        if (this.ref == null) {
            List<Link> selfLinks = getLinks("self");
            if (selfLinks == null || selfLinks.isEmpty()) {
                return null;
            }
            this.ref = new LinkEntityRef(selfLinks.get(0).getId(), selfLinks.get(0).getHref());
        }
        return this.ref;
    }

    public String getSmallImageURL() {
        if (this.mSmallProfilePhotoURL == null) {
            this.mSmallProfilePhotoURL = getLinks(REF_SMALL) != null ? getLinks(REF_SMALL).get(0).getHref() : null;
        }
        return this.mSmallProfilePhotoURL;
    }

    public String getMediumImageURL() {
        if (this.mMediumProfilePhotoURL == null) {
            this.mMediumProfilePhotoURL = getLinks(REF_MEDIUM) != null ? getLinks(REF_MEDIUM).get(0).getHref() : null;
        }
        return this.mMediumProfilePhotoURL;
    }

    public String getLargeImageURL() {
        if (this.mLargeProfilePhotoURL == null) {
            this.mLargeProfilePhotoURL = getLinks(REF_LARGE) != null ? getLinks(REF_LARGE).get(0).getHref() : null;
        }
        return this.mLargeProfilePhotoURL;
    }

    public void setRef(EntityRef<UserProfilePhoto> ref2) {
        this.ref = ref2;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.mSmallProfilePhotoURL = smallImageURL;
    }

    public void setMediumImageURL(String mediumImageURL) {
        this.mMediumProfilePhotoURL = mediumImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.mLargeProfilePhotoURL = largeImageURL;
    }

    public ImageUrlImpl toImageUrl() {
        return ImageUrlImpl.getBuilder().setSmall(getSmallImageURL()).setMedium(getMediumImageURL()).setLarge(getLargeImageURL()).build();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mSmallProfilePhotoURL);
        dest.writeString(this.mMediumProfilePhotoURL);
        dest.writeString(this.mLargeProfilePhotoURL);
    }

    private UserProfilePhotoImpl(Parcel in) {
        super(in);
        this.mSmallProfilePhotoURL = in.readString();
        this.mMediumProfilePhotoURL = in.readString();
        this.mLargeProfilePhotoURL = in.readString();
    }
}
