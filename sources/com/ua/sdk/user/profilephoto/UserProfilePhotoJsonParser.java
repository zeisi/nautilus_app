package com.ua.sdk.user.profilephoto;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class UserProfilePhotoJsonParser implements JsonParser<UserProfilePhoto> {
    private Gson gson;

    public UserProfilePhotoJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public UserProfilePhotoImpl parse(InputStream inputStream) throws UaException {
        try {
            return UserProfilePhotoTransferObject.toUserProfilePhotoImpl((UserProfilePhotoTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) UserProfilePhotoTransferObject.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
