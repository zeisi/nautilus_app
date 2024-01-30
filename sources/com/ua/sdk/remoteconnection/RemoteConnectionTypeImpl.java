package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Precondition;
import java.util.List;

public class RemoteConnectionTypeImpl extends ApiTransferObject implements RemoteConnectionType, Parcelable {
    public static Parcelable.Creator<RemoteConnectionTypeImpl> CREATOR = new Parcelable.Creator<RemoteConnectionTypeImpl>() {
        public RemoteConnectionTypeImpl createFromParcel(Parcel source) {
            return new RemoteConnectionTypeImpl(source);
        }

        public RemoteConnectionTypeImpl[] newArray(int size) {
            return new RemoteConnectionTypeImpl[size];
        }
    };
    private String disconnectCancelStr;
    private String disconnectConfirmStr;
    private String disconnectStr;
    private String introBodyStr;
    private String introHeadingStr;
    private String logoLink;
    private String logoLinkLight;
    private String name;
    private String oAuthLink;
    private String recorderTypeKey;
    private String type;

    protected RemoteConnectionTypeImpl() {
    }

    protected RemoteConnectionTypeImpl(RemoteConnectionType remoteConnectionType) {
        Precondition.isNotNull(remoteConnectionType, "remoteConnectionType");
        this.type = remoteConnectionType.getType();
        this.recorderTypeKey = remoteConnectionType.getRecorderTypeKey();
        this.name = remoteConnectionType.getName();
        this.introHeadingStr = remoteConnectionType.getIntroHeadingStr();
        this.introBodyStr = remoteConnectionType.getIntroBodyStr();
        this.disconnectStr = remoteConnectionType.getDisconnectStr();
        this.disconnectCancelStr = remoteConnectionType.getDisconnectCancelStr();
        this.disconnectConfirmStr = remoteConnectionType.getDisconnectConfirmStr();
        this.logoLink = remoteConnectionType.getLogoLink();
        this.logoLinkLight = remoteConnectionType.getLogoLinkLight();
        this.oAuthLink = remoteConnectionType.getOAuthLink();
        if (remoteConnectionType instanceof RemoteConnectionTypeImpl) {
            copyLinkMap(((RemoteConnectionTypeImpl) remoteConnectionType).getLinkMap());
        }
    }

    public EntityRef<RemoteConnectionType> getRef() {
        List<Link> selfLinks = getLinks("self");
        if (selfLinks == null || selfLinks.isEmpty()) {
            return null;
        }
        return new LinkEntityRef(selfLinks.get(0).getId(), selfLinks.get(0).getHref());
    }

    public int describeContents() {
        return 0;
    }

    public String getType() {
        return this.type;
    }

    public String getRecorderTypeKey() {
        return this.recorderTypeKey;
    }

    public String getName() {
        return this.name;
    }

    public String getIntroHeadingStr() {
        return this.introHeadingStr;
    }

    public String getIntroBodyStr() {
        return this.introBodyStr;
    }

    public String getDisconnectStr() {
        return this.disconnectStr;
    }

    public String getDisconnectCancelStr() {
        return this.disconnectCancelStr;
    }

    public String getDisconnectConfirmStr() {
        return this.disconnectConfirmStr;
    }

    public String getLogoLink() {
        return this.logoLink;
    }

    public String getLogoLinkLight() {
        return this.logoLinkLight;
    }

    public String getOAuthLink() {
        return this.oAuthLink;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public void setRecorderTypeKey(String recorderTypeKey2) {
        this.recorderTypeKey = recorderTypeKey2;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setIntroHeadingStr(String introHeadingStr2) {
        this.introHeadingStr = introHeadingStr2;
    }

    public void setIntroBodyStr(String introBodyStr2) {
        this.introBodyStr = introBodyStr2;
    }

    public void setDisconnectStr(String disconnectStr2) {
        this.disconnectStr = disconnectStr2;
    }

    public void setDisconnectCancelStr(String disconnectCancelStr2) {
        this.disconnectCancelStr = disconnectCancelStr2;
    }

    public void setDisconnectConfirmStr(String disconnectConfirmStr2) {
        this.disconnectConfirmStr = disconnectConfirmStr2;
    }

    public void setLogoLink(String logoLink2) {
        this.logoLink = logoLink2;
    }

    public void setLogoLinkLight(String logoLinkLight2) {
        this.logoLinkLight = logoLinkLight2;
    }

    public void setOAuthLink(String oAuthLink2) {
        this.oAuthLink = oAuthLink2;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.type);
        dest.writeString(this.recorderTypeKey);
        dest.writeString(this.name);
        dest.writeString(this.introHeadingStr);
        dest.writeString(this.introBodyStr);
        dest.writeString(this.disconnectStr);
        dest.writeString(this.disconnectCancelStr);
        dest.writeString(this.disconnectConfirmStr);
        dest.writeString(this.logoLink);
        dest.writeString(this.logoLinkLight);
        dest.writeString(this.oAuthLink);
    }

    private RemoteConnectionTypeImpl(Parcel in) {
        super(in);
        this.type = in.readString();
        this.recorderTypeKey = in.readString();
        this.name = in.readString();
        this.introHeadingStr = in.readString();
        this.introBodyStr = in.readString();
        this.disconnectStr = in.readString();
        this.disconnectCancelStr = in.readString();
        this.disconnectConfirmStr = in.readString();
        this.logoLink = in.readString();
        this.logoLinkLight = in.readString();
        this.oAuthLink = in.readString();
    }
}
