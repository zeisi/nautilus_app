package com.ua.sdk.user.profilephoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ua.sdk.cache.database.LegacyEntityDatabase;
import com.ua.sdk.suggestedfriends.SuggestedFriendsImpl;

public class UserProfilePhotoDatabase extends LegacyEntityDatabase<UserProfilePhoto> {
    public UserProfilePhotoDatabase(Context context) {
        super(context, SuggestedFriendsImpl.REF_PROFILE_PIC, (String) null, (String[]) null, (String) null, 1);
    }

    /* access modifiers changed from: protected */
    public void createEntityTable(SQLiteDatabase db) {
    }

    public void onEntityUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /* access modifiers changed from: protected */
    public UserProfilePhotoImpl getEntityFromCursor(Cursor cursor) {
        return null;
    }

    /* access modifiers changed from: protected */
    public ContentValues getContentValuesFromEntity(UserProfilePhoto entity) {
        return null;
    }
}
