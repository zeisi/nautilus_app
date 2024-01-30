package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryGroupLeaderboard;
import com.ua.sdk.activitystory.ActivityStoryGroupLeaderboardObject;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityStoryGroupLeaderboardObjectImpl extends ApiTransferObject implements ActivityStoryGroupLeaderboardObject {
    public static Parcelable.Creator<ActivityStoryGroupLeaderboardObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryGroupLeaderboardObjectImpl>() {
        public ActivityStoryGroupLeaderboardObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryGroupLeaderboardObjectImpl(source);
        }

        public ActivityStoryGroupLeaderboardObjectImpl[] newArray(int size) {
            return new ActivityStoryGroupLeaderboardObjectImpl[size];
        }
    };
    @SerializedName("end_time")
    private Date endTime;
    @SerializedName("leaderboard")
    private List<ActivityStoryGroupLeaderboard> leaderboard;
    @SerializedName("result")
    private ActivityStoryGroupLeaderboard result;
    @SerializedName("start_time")
    private Date startTime;

    public ActivityStoryGroupLeaderboardObjectImpl() {
        this.leaderboard = new ArrayList(10);
    }

    private ActivityStoryGroupLeaderboardObjectImpl(Parcel in) {
        super(in);
        this.leaderboard = new ArrayList(10);
        this.startTime = (Date) in.readValue(Date.class.getClassLoader());
        this.endTime = (Date) in.readValue(Date.class.getClassLoader());
        in.readList(this.leaderboard, ActivityStoryGroupLeaderboard.class.getClassLoader());
        this.result = (ActivityStoryGroupLeaderboard) in.readValue(ActivityStoryGroupLeaderboard.class.getClassLoader());
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime2) {
        this.startTime = startTime2;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime2) {
        this.endTime = endTime2;
    }

    public List<ActivityStoryGroupLeaderboard> getLeaderboard() {
        return this.leaderboard;
    }

    public void setLeaderboard(List<ActivityStoryGroupLeaderboard> leaderboard2) {
        this.leaderboard.clear();
        this.leaderboard.addAll(leaderboard2);
    }

    public ActivityStoryGroupLeaderboard getResult() {
        return this.result;
    }

    public void setResult(ActivityStoryGroupLeaderboard result2) {
        this.result = result2;
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.GROUP_LEADERBOARD;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.startTime);
        dest.writeValue(this.endTime);
        dest.writeList(this.leaderboard);
        dest.writeValue(this.result);
    }
}
