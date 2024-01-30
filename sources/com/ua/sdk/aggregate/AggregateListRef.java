package com.ua.sdk.aggregate;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.oss.org.codehaus.jackson.map.util.Iso8601DateFormat;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.UaException;
import com.ua.sdk.datapoint.DataType;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Period;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AggregateListRef implements EntityListRef<Aggregate> {
    public static Parcelable.Creator<AggregateListRef> CREATOR = new Parcelable.Creator<AggregateListRef>() {
        public AggregateListRef createFromParcel(Parcel source) {
            return new AggregateListRef(source);
        }

        public AggregateListRef[] newArray(int size) {
            return new AggregateListRef[size];
        }
    };
    private final String href;

    private AggregateListRef(Parcel in) {
        this.href = in.readString();
    }

    private AggregateListRef(Builder init) {
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
        private static final String DATA_TYPES_KEY = "data_types";
        private static final String END_DATE_KEY = "end_datetime";
        private static final String PERIOD_KEY = "period";
        private static final String START_DATE_KEY = "start_datetime";
        private static final String USER_ID_KEY = "user_id";
        List<String> dataTypes;
        Date endDate;
        Period period;
        Date startDate;
        String userId;

        public Builder() {
            super(UrlBuilderImpl.GET_AGGREGATE_LIST_URL);
        }

        public Builder setStartDate(Date startDate2) {
            this.startDate = startDate2;
            setParam(START_DATE_KEY, Iso8601DateFormat.format(startDate2));
            return this;
        }

        public Builder setEndDate(Date endDate2) {
            this.endDate = endDate2;
            setParam(END_DATE_KEY, Iso8601DateFormat.format(endDate2));
            return this;
        }

        public Builder setPeriod(Period period2) {
            this.period = period2;
            setParam(PERIOD_KEY, period2.toString());
            return this;
        }

        public Builder setUser(EntityRef<User> userRef) {
            this.userId = userRef.getId();
            setParam("user_id", this.userId);
            return this;
        }

        public Builder addDataType(DataType dataType) {
            if (this.dataTypes == null) {
                this.dataTypes = new ArrayList();
            }
            this.dataTypes.add(dataType.getId());
            return this;
        }

        public AggregateListRef build() throws UaException {
            Precondition.isNotNull(this.userId, "userId");
            Precondition.isNotNull(this.startDate, "startDate");
            Precondition.isNotNull(this.endDate, "endDate");
            Precondition.isNotNull(this.dataTypes, "dataTypes");
            if (this.period != null) {
                if (!this.period.isValid(Period.ONE_YEAR, Period.ONE_MONTH, Period.ONE_DAY)) {
                    throw new UaException(this.period.toString() + " is not valid...P1Y, P1M, P1D are only supported for aggregates!");
                }
            }
            StringBuilder sb = new StringBuilder();
            for (String dataType : this.dataTypes) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(dataType);
            }
            setParam(DATA_TYPES_KEY, sb.toString());
            return new AggregateListRef(this);
        }
    }
}
