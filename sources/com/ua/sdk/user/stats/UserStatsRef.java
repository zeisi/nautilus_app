package com.ua.sdk.user.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.ua.sdk.EntityRef;
import com.ua.sdk.LocalDate;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;

public class UserStatsRef implements EntityRef<UserStats> {
    public static Parcelable.Creator<UserStatsRef> CREATOR = new Parcelable.Creator<UserStatsRef>() {
        public UserStatsRef createFromParcel(Parcel source) {
            return new UserStatsRef(source);
        }

        public UserStatsRef[] newArray(int size) {
            return new UserStatsRef[size];
        }
    };
    private String href;

    private UserStatsRef(Builder init) {
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

    public static final class Builder extends BaseReferenceBuilder {
        private Builder() {
            super("v7.0/user_stats/{id}/");
        }

        public Builder setUser(EntityRef<User> user) {
            setParam("id", user.getId());
            return this;
        }

        public Builder setAggregatePeriodUserStats(AggregatePeriodUserStats aggregatePeriodUserStats) {
            setParam("aggregate_by_period", aggregatePeriodUserStats.toString());
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            setParam(TrainingGoal.START_DATE, startDate.toString());
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            setParam("end_date", endDate.toString());
            return this;
        }

        public Builder setIncludeSummaries(boolean includeSummaries) {
            setParam("include_summary_stats", Boolean.toString(includeSummaries));
            return this;
        }

        public UserStatsRef build() {
            UserStatsRef userStatsRef;
            synchronized (UserStatsRef.class) {
                Precondition.isNotNull(getParam("user"));
                userStatsRef = new UserStatsRef(this);
            }
            return userStatsRef;
        }
    }

    public enum AggregatePeriodUserStats {
        DAY("day"),
        WEEK("week"),
        MONTH("month"),
        YEAR("year"),
        LIFETIME("lifetime");
        
        private String value;

        private AggregatePeriodUserStats(String value2) {
            this.value = value2;
        }

        public String toString() {
            return this.value;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private UserStatsRef(Parcel in) {
        this.href = in.readString();
    }
}
