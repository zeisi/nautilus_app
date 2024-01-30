package com.ua.sdk.user.profilephoto;

import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class UserProfilePhotoService extends AbstractResourceService<UserProfilePhoto> {
    public UserProfilePhotoService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<UserProfilePhoto> userProfilePhotoParser) {
        super(connFactory, authManager, urlBuilder, userProfilePhotoParser, (JsonWriter) null, (JsonParser) null);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create UserProfilePhotos not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(UserProfilePhoto resource) {
        throw new UnsupportedOperationException("Save UserProfilePhotos not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete UserProfilePhotos not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetUserProfilePhotoUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<UserProfilePhoto> entityListRef) {
        throw new UnsupportedOperationException("Fetch UserProfilePhotos page not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch UserProfilePhotos not supported.");
    }
}
