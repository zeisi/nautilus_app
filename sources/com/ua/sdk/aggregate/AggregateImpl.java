package com.ua.sdk.aggregate;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.datapoint.DataType;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Period;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.List;

public class AggregateImpl extends ApiTransferObject implements Aggregate {
    public static Parcelable.Creator<AggregateImpl> CREATOR = new Parcelable.Creator<AggregateImpl>() {
        public AggregateImpl createFromParcel(Parcel source) {
            return new AggregateImpl(source);
        }

        public AggregateImpl[] newArray(int size) {
            return new AggregateImpl[size];
        }
    };
    protected static final String LINK_DATA_TYPE = "data_type";
    protected static final String LINK_USER = "user";
    transient EntityRef<DataType> dataTypeRef;
    @SerializedName("period")
    protected Period period;
    @SerializedName("periods")
    protected List<AggregateSummary> periods;
    @SerializedName("summary")
    protected AggregateSummary summary;
    transient EntityRef<User> userRef;

    public AggregateImpl() {
    }

    protected AggregateImpl(Parcel in) {
        super(in);
        this.periods = new ArrayList();
        this.summary = (AggregateSummary) in.readValue(AggregateSummaryImpl.class.getClassLoader());
        in.readList(this.periods, AggregateSummary.class.getClassLoader());
        this.period = (Period) in.readValue(Period.class.getClassLoader());
    }

    public AggregateSummary getSummary() {
        return this.summary;
    }

    public void setSummary(AggregateSummary summary2) {
        this.summary = summary2;
    }

    public List<AggregateSummary> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<AggregateSummary> periods2) {
        if (periods2 == null) {
            periods2 = new ArrayList<>();
        }
        periods2.clear();
        periods2.addAll(periods2);
    }

    public Period getPeriod() {
        return this.period;
    }

    public void setPeriod(Period period2) {
        this.period = period2;
    }

    public EntityRef<User> getUserRef() {
        Link user;
        if (this.userRef == null && (user = getLink("user")) != null) {
            this.userRef = new LinkEntityRef(user.getId(), user.getHref());
        }
        return this.userRef;
    }

    public void setUserRef(EntityRef<User> userRef2) {
        if (userRef2 != null) {
            this.userRef = userRef2;
            addLink("user", new Link(userRef2.getHref(), userRef2.getId()));
        }
    }

    public EntityRef<DataType> getDataTypeRef() {
        Link dataType;
        if (this.dataTypeRef == null && (dataType = getLink(LINK_DATA_TYPE)) != null) {
            this.dataTypeRef = new LinkEntityRef(dataType.getId(), dataType.getHref());
        }
        return this.dataTypeRef;
    }

    public void setDataTypeRef(EntityRef<DataType> dataTypeRef2) {
        if (dataTypeRef2 != null) {
            this.dataTypeRef = dataTypeRef2;
            addLink(LINK_DATA_TYPE, new Link(dataTypeRef2.getHref(), dataTypeRef2.getId()));
        }
    }

    public EntityRef<Aggregate> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (this.periods == null) {
            this.periods = new ArrayList();
        }
        dest.writeValue(this.summary);
        dest.writeList(this.periods);
        dest.writeValue(this.period);
    }
}
