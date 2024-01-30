package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.workout.WorkoutSummary;
import java.util.Date;

public class WorkoutSummaryImpl extends ApiTransferObject implements WorkoutSummary, Parcelable {
    public static Parcelable.Creator<WorkoutSummaryImpl> CREATOR = new Parcelable.Creator<WorkoutSummaryImpl>() {
        public WorkoutSummaryImpl createFromParcel(Parcel source) {
            return new WorkoutSummaryImpl(source);
        }

        public WorkoutSummaryImpl[] newArray(int size) {
            return new WorkoutSummaryImpl[size];
        }
    };
    private ActigraphyAggregatesImpl mActigraphyAggregates;
    private int mActivityTypeId;
    private Date mEndDateTime;
    private String mName;
    private Date mStartDateTime;

    protected WorkoutSummaryImpl() {
    }

    protected WorkoutSummaryImpl(int activityTypeId, String name, ActigraphyAggregatesImpl actigraphyAggregates, Date startDateTime, Date endDateTime) {
        this.mActivityTypeId = activityTypeId;
        this.mName = name;
        this.mActigraphyAggregates = actigraphyAggregates;
        this.mStartDateTime = startDateTime;
        this.mEndDateTime = endDateTime;
    }

    public EntityRef<WorkoutSummary> getRef() {
        return null;
    }

    public void setActivityTypeId(int activityTypeId) {
        this.mActivityTypeId = activityTypeId;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setWorkoutAggregates(ActigraphyAggregatesImpl actigraphyAggregatesImpl) {
        this.mActigraphyAggregates = actigraphyAggregatesImpl;
    }

    public void setStartDateTime(Date startDateTime) {
        this.mStartDateTime = startDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.mEndDateTime = endDateTime;
    }

    public int getActivityTypeId() {
        return this.mActivityTypeId;
    }

    public String getName() {
        return this.mName;
    }

    public ActigraphyAggregatesImpl getWorkoutAggregates() {
        return this.mActigraphyAggregates;
    }

    public Date getStartDateTime() {
        return this.mStartDateTime;
    }

    public Date getEndDateTime() {
        return this.mEndDateTime;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        long j = -1;
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mActivityTypeId);
        dest.writeString(this.mName);
        dest.writeParcelable(this.mActigraphyAggregates, flags);
        dest.writeLong(this.mStartDateTime == null ? -1 : this.mStartDateTime.getTime());
        if (this.mEndDateTime != null) {
            j = this.mEndDateTime.getTime();
        }
        dest.writeLong(j);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private WorkoutSummaryImpl(Parcel in) {
        super(in);
        Date date = null;
        this.mActivityTypeId = in.readInt();
        this.mName = in.readString();
        this.mActigraphyAggregates = (ActigraphyAggregatesImpl) in.readParcelable(ActigraphyAggregatesImpl.class.getClassLoader());
        Long tmpStartDateTime = Long.valueOf(in.readLong());
        this.mStartDateTime = tmpStartDateTime.longValue() == -1 ? null : new Date(tmpStartDateTime.longValue());
        Long tmpEndDateTime = Long.valueOf(in.readLong());
        this.mEndDateTime = tmpEndDateTime.longValue() != -1 ? new Date(tmpEndDateTime.longValue()) : date;
    }
}
