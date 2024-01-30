package com.ua.sdk.group.leaderboard;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.aggregate.AggregateImpl;

public class GroupLeaderboardImpl extends AggregateImpl implements GroupLeaderboard {
    public static Parcelable.Creator<GroupLeaderboardImpl> CREATOR = new Parcelable.Creator<GroupLeaderboardImpl>() {
        public GroupLeaderboardImpl createFromParcel(Parcel source) {
            return new GroupLeaderboardImpl(source);
        }

        public GroupLeaderboardImpl[] newArray(int size) {
            return new GroupLeaderboardImpl[size];
        }
    };

    public GroupLeaderboardImpl() {
    }

    private GroupLeaderboardImpl(Parcel in) {
        super(in);
    }
}
