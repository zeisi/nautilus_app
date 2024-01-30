package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.LocalDate;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ActigraphyImpl extends ApiTransferObject implements Actigraphy, Parcelable {
    public static Parcelable.Creator<ActigraphyImpl> CREATOR = new Parcelable.Creator<ActigraphyImpl>() {
        public ActigraphyImpl createFromParcel(Parcel source) {
            return new ActigraphyImpl(source);
        }

        public ActigraphyImpl[] newArray(int size) {
            return new ActigraphyImpl[size];
        }
    };
    protected static final String REF_USER = "user";
    private ActigraphyAggregatesImpl mActigraphyAggregatesImpl;
    private ActigraphyMetricsImpl mActigraphyMetricsImpl;
    private LocalDate mDate;
    private Date mEndDateTime;
    private Date mStartDateTime;
    private TimeZone mTimeZone;
    private WorkoutSummaryImpl[] mWorkoutSummaries;

    protected ActigraphyImpl() {
    }

    public void setAggregates(ActigraphyAggregatesImpl aggregates) {
        this.mActigraphyAggregatesImpl = aggregates;
    }

    public EntityRef<Actigraphy> getRef() {
        if (this.mStartDateTime != null) {
            return new LinkEntityRef(String.valueOf(this.mStartDateTime.getTime()), (String) null);
        }
        return null;
    }

    public EntityRef<User> getUserRef() {
        List<Link> userLinks = getLinks("user");
        if (userLinks == null || userLinks.isEmpty()) {
            return null;
        }
        return new LinkEntityRef(userLinks.get(0).getId(), userLinks.get(0).getHref());
    }

    public void setMetrics(ActigraphyMetricsImpl metrics) {
        this.mActigraphyMetricsImpl = metrics;
    }

    public void setWorkoutSummaries(WorkoutSummaryImpl[] workoutSummaries) {
        this.mWorkoutSummaries = workoutSummaries;
    }

    public void setDate(LocalDate date) {
        this.mDate = date;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    public void setStartDateTime(Date startDateTime) {
        this.mStartDateTime = startDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.mEndDateTime = endDateTime;
    }

    public ActigraphyAggregatesImpl getActigraphyAggregates() {
        return this.mActigraphyAggregatesImpl;
    }

    public ActigraphyMetricsImpl getMetrics() {
        return this.mActigraphyMetricsImpl;
    }

    public WorkoutSummaryImpl[] getWorkoutSummaries() {
        return this.mWorkoutSummaries;
    }

    public LocalDate getDate() {
        return this.mDate;
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
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
        long j;
        long j2 = -1;
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mActigraphyAggregatesImpl, flags);
        dest.writeParcelable(this.mActigraphyMetricsImpl, flags);
        dest.writeParcelableArray(this.mWorkoutSummaries, flags);
        dest.writeParcelable(this.mDate, flags);
        dest.writeString(this.mTimeZone != null ? this.mTimeZone.getID() : null);
        if (this.mStartDateTime != null) {
            j = this.mStartDateTime.getTime();
        } else {
            j = -1;
        }
        dest.writeLong(j);
        if (this.mEndDateTime != null) {
            j2 = this.mEndDateTime.getTime();
        }
        dest.writeLong(j2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private ActigraphyImpl(Parcel in) {
        super(in);
        Date date = null;
        this.mActigraphyAggregatesImpl = (ActigraphyAggregatesImpl) in.readParcelable(ActigraphyAggregatesImpl.class.getClassLoader());
        this.mActigraphyMetricsImpl = (ActigraphyMetricsImpl) in.readParcelable(ActigraphyMetricsImpl.class.getClassLoader());
        Parcelable[] tempWorkoutSummaries = in.readParcelableArray(WorkoutSummaryImpl.class.getClassLoader());
        if (tempWorkoutSummaries != null) {
            this.mWorkoutSummaries = new WorkoutSummaryImpl[tempWorkoutSummaries.length];
            System.arraycopy(tempWorkoutSummaries, 0, this.mWorkoutSummaries, 0, tempWorkoutSummaries.length);
        } else {
            this.mWorkoutSummaries = null;
        }
        this.mDate = (LocalDate) in.readParcelable(LocalDate.class.getClassLoader());
        String timeZone = in.readString();
        this.mTimeZone = (timeZone == null || timeZone.length() == 0) ? null : TimeZone.getTimeZone(timeZone);
        Long tmpStartDateTime = Long.valueOf(in.readLong());
        this.mStartDateTime = tmpStartDateTime.longValue() == -1 ? null : new Date(tmpStartDateTime.longValue());
        Long tmpEndDateTime = Long.valueOf(in.readLong());
        this.mEndDateTime = tmpEndDateTime.longValue() != -1 ? new Date(tmpEndDateTime.longValue()) : date;
    }
}
