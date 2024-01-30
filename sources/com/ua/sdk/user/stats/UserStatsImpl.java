package com.ua.sdk.user.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStatsImpl extends ApiTransferObject implements UserStats {
    public static Parcelable.Creator<UserStatsImpl> CREATOR = new Parcelable.Creator<UserStatsImpl>() {
        public UserStatsImpl createFromParcel(Parcel source) {
            return new UserStatsImpl(source);
        }

        public UserStatsImpl[] newArray(int size) {
            return new UserStatsImpl[size];
        }
    };
    @SerializedName("_embedded")
    Map<String, ArrayList<Stats>> embeddedStats;
    transient ArrayList<Stats> stats;
    transient ArrayList<Stats> summaryStats;

    public UserStatsImpl() {
    }

    public EntityRef<User> getUserRef() {
        Link link = getLink("user");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public List<Stats> getStats() {
        if (this.embeddedStats == null) {
            return null;
        }
        if (this.stats == null) {
            this.stats = this.embeddedStats.get("stats");
        }
        return this.stats;
    }

    public boolean hasSummaryStats() {
        if (this.embeddedStats == null) {
            return false;
        }
        return this.embeddedStats.containsKey("summary_stats");
    }

    public List<Stats> getSummaryStats() {
        if (!hasSummaryStats()) {
            return null;
        }
        if (this.summaryStats == null) {
            this.summaryStats = this.embeddedStats.get("summary_stats");
        }
        return this.summaryStats;
    }

    public EntityRef getRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.stats != null ? this.stats : new ArrayList<>(0));
        dest.writeList(this.summaryStats != null ? this.summaryStats : new ArrayList<>(0));
    }

    private UserStatsImpl(Parcel in) {
        HashMap hashMap = null;
        this.stats = new ArrayList<>();
        in.readList(this.stats, Stats.class.getClassLoader());
        this.stats = this.stats.size() == 0 ? null : this.stats;
        this.summaryStats = new ArrayList<>();
        in.readList(this.summaryStats, Stats.class.getClassLoader());
        this.summaryStats = this.summaryStats.size() == 0 ? null : this.summaryStats;
        this.embeddedStats = this.stats != null ? new HashMap() : hashMap;
        if (this.embeddedStats != null) {
            this.embeddedStats.put("stats", this.stats);
            if (this.summaryStats != null) {
                this.embeddedStats.put("summary_stats", this.summaryStats);
            }
        }
    }
}
