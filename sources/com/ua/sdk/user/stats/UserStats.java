package com.ua.sdk.user.stats;

import android.os.Parcelable;
import com.ua.sdk.Entity;
import com.ua.sdk.EntityRef;
import com.ua.sdk.user.User;
import java.util.List;

public interface UserStats extends Entity, Parcelable {
    List<Stats> getStats();

    List<Stats> getSummaryStats();

    EntityRef<User> getUserRef();

    boolean hasSummaryStats();
}
