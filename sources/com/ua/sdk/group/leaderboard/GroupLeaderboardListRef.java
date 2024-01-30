package com.ua.sdk.group.leaderboard;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobsandgeeks.saripaar.DateFormats;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.group.Group;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupLeaderboardListRef implements EntityListRef<GroupLeaderboard> {
    public static Parcelable.Creator<GroupLeaderboardListRef> CREATOR = new Parcelable.Creator<GroupLeaderboardListRef>() {
        public GroupLeaderboardListRef createFromParcel(Parcel source) {
            return new GroupLeaderboardListRef(source);
        }

        public GroupLeaderboardListRef[] newArray(int size) {
            return new GroupLeaderboardListRef[size];
        }
    };
    private final String href;

    private GroupLeaderboardListRef(Parcel in) {
        this.href = in.readString();
    }

    private GroupLeaderboardListRef(Builder init) {
        this.href = init.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    public static class Builder extends BaseReferenceBuilder {
        private static final String END_DATE_KEY = "end_datetime";
        private static final String GROUP_ID_KEY = "group_id";
        private static final String ITERATION_KEY = "iteration";
        private static final String START_DATE_KEY = "start_datetime";
        Date endDate;
        String groupId;
        Integer iteration;
        Date startDate;

        public Builder() {
            super(UrlBuilderImpl.GET_GROUP_LEADERBOARD_LIST_URL);
        }

        public Builder setStartDate(Date startDate2) {
            this.startDate = startDate2;
            return this;
        }

        public Builder setEndDate(Date endDate2) {
            this.endDate = endDate2;
            return this;
        }

        public Builder setGroup(EntityRef<Group> groupRef) {
            this.groupId = groupRef.getId();
            return this;
        }

        public Builder setIteration(int iteration2) {
            this.iteration = Integer.valueOf(iteration2);
            return this;
        }

        public GroupLeaderboardListRef build() {
            Precondition.isNotNull(this.groupId);
            setParam(GROUP_ID_KEY, this.groupId);
            if (this.iteration != null) {
                setParam(ITERATION_KEY, this.iteration.intValue());
                return new GroupLeaderboardListRef(this);
            }
            Precondition.isNotNull(this.startDate);
            Precondition.isNotNull(this.endDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormats.YMD);
            setParam(START_DATE_KEY, dateFormat.format(this.startDate));
            setParam(END_DATE_KEY, dateFormat.format(this.endDate));
            return new GroupLeaderboardListRef(this);
        }
    }
}
