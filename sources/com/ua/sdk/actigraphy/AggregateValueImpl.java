package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.aggregate.AggregateDetails;
import com.ua.sdk.aggregate.AggregateValue;

public class AggregateValueImpl implements Parcelable, AggregateValue {
    public static Parcelable.Creator<AggregateValueImpl> CREATOR = new Parcelable.Creator<AggregateValueImpl>() {
        public AggregateValueImpl createFromParcel(Parcel source) {
            return new AggregateValueImpl(source);
        }

        public AggregateValueImpl[] newArray(int size) {
            return new AggregateValueImpl[size];
        }
    };
    private AggregateDetails mAggregateDetails;
    private Double mAverage;
    private Double mCount;
    private Double mLatest;
    private Double mMax;
    private Double mMin;
    private Double mSum;

    protected AggregateValueImpl() {
    }

    public Double getCount() {
        return this.mCount;
    }

    public void setCount(Double count) {
        this.mCount = count;
    }

    public Double getSum() {
        return this.mSum;
    }

    public void setSum(Double sum) {
        this.mSum = sum;
    }

    public Double getMin() {
        return this.mMin;
    }

    public void setMin(Double min) {
        this.mMin = min;
    }

    public Double getMax() {
        return this.mMax;
    }

    public void setMax(Double max) {
        this.mMax = max;
    }

    public Double getLatest() {
        return this.mLatest;
    }

    public void setLatest(Double latest) {
        this.mLatest = latest;
    }

    public Double getAverage() {
        return this.mAverage;
    }

    public void setAverage(Double average) {
        this.mAverage = average;
    }

    public AggregateDetails getAggregateDetails() {
        return this.mAggregateDetails;
    }

    public void setAggregateDetails(AggregateDetails aggregateDetails) {
        this.mAggregateDetails = aggregateDetails;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        String str = null;
        dest.writeString(this.mSum == null ? null : this.mSum.toString());
        dest.writeString(this.mMin == null ? null : this.mMin.toString());
        dest.writeString(this.mMax == null ? null : this.mMax.toString());
        dest.writeString(this.mLatest == null ? null : this.mLatest.toString());
        dest.writeString(this.mCount == null ? null : this.mCount.toString());
        if (this.mAverage != null) {
            str = this.mAverage.toString();
        }
        dest.writeString(str);
        dest.writeParcelable(this.mAggregateDetails, flags);
    }

    private AggregateValueImpl(Parcel in) {
        Double d = null;
        String tmpString = in.readString();
        this.mSum = tmpString == null ? null : new Double(tmpString);
        String tmpString2 = in.readString();
        this.mMin = tmpString2 == null ? null : new Double(tmpString2);
        String tmpString3 = in.readString();
        this.mMax = tmpString3 == null ? null : new Double(tmpString3);
        String tmpString4 = in.readString();
        this.mLatest = tmpString4 == null ? null : new Double(tmpString4);
        String tmpString5 = in.readString();
        this.mCount = tmpString5 == null ? null : new Double(tmpString5);
        String tmpString6 = in.readString();
        this.mAverage = tmpString6 != null ? new Double(tmpString6) : d;
        this.mAggregateDetails = (AggregateDetails) in.readParcelable(AggregateDetails.class.getClassLoader());
    }
}
