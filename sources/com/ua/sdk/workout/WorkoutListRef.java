package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.oss.org.codehaus.jackson.map.util.Iso8601DateFormat;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import com.ua.sdk.user.User;
import java.util.Date;

public class WorkoutListRef implements EntityListRef<Workout> {
    public static final Parcelable.Creator<WorkoutListRef> CREATOR = new Parcelable.Creator<WorkoutListRef>() {
        public WorkoutListRef createFromParcel(Parcel source) {
            return new WorkoutListRef(source);
        }

        public WorkoutListRef[] newArray(int size) {
            return new WorkoutListRef[size];
        }
    };
    private String href;

    public enum WorkoutOrder {
        CHRONOLOGICAL,
        LATEST_FIRST
    }

    public WorkoutListRef(String href2) {
        this.href = href2;
    }

    private WorkoutListRef(Builder init) {
        this.href = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        private static final String KEY_ACTIVITY_TYPE = "activity_type";
        private static final String KEY_ORDER_BY = "order_by";
        private static final String KEY_STARTED_AFTER = "started_after";
        private static final String KEY_STARTED_BEFORE = "started_before";
        private static final String KEY_USER = "user";
        private Date startedAfter;
        private Date startedBefore;
        private EntityRef<User> user;

        private Builder() {
            super(UrlBuilderImpl.GET_WORKOUTS_URL);
        }

        public Builder setActivityTypes(EntityRef<ActivityType>... activityTypes) {
            if (activityTypes != null) {
                String[] activityTypeIds = new String[activityTypes.length];
                for (int i = 0; i < activityTypes.length; i++) {
                    activityTypeIds[i] = activityTypes[i].getId();
                }
                setParams(KEY_ACTIVITY_TYPE, activityTypeIds);
            } else {
                setParams(KEY_ACTIVITY_TYPE, (String[]) null);
            }
            return this;
        }

        public Builder setStartedAfter(Date startedAfter2) {
            if (startedAfter2 != null) {
                setParam(KEY_STARTED_AFTER, Iso8601DateFormat.format(startedAfter2));
                this.startedAfter = startedAfter2;
            }
            return this;
        }

        public Builder setStartedBefore(Date startedBefore2) {
            if (startedBefore2 != null) {
                setParam(KEY_STARTED_BEFORE, Iso8601DateFormat.format(startedBefore2));
                this.startedBefore = startedBefore2;
            }
            return this;
        }

        public Builder setUser(EntityRef<User> user2) {
            if (user2 != null) {
                this.user = user2;
                setParam("user", user2.getId());
            } else {
                setParam("user", (String) null);
            }
            return this;
        }

        public Builder setWorkoutOrder(WorkoutOrder order) {
            if (order == WorkoutOrder.LATEST_FIRST) {
                setParam(KEY_ORDER_BY, "-start_datetime");
            }
            return this;
        }

        public WorkoutListRef build() {
            if (this.user == null) {
                throw new IllegalArgumentException("Must call setUser before building.");
            }
            if (!(this.startedAfter == null || this.startedBefore == null)) {
                Precondition.check(this.startedAfter.getTime() < this.startedBefore.getTime(), "Started after not should be less than started before date");
            }
            return new WorkoutListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private WorkoutListRef(Parcel in) {
        this.href = in.readString();
    }
}
