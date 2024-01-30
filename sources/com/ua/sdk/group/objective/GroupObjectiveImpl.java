package com.ua.sdk.group.objective;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataField;
import com.ua.sdk.datapoint.DataType;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Period;
import com.ua.sdk.net.json.Iso8601PeriodFormat;
import java.util.Date;

public class GroupObjectiveImpl extends ApiTransferObject implements GroupObjective {
    public static Parcelable.Creator<GroupObjectiveImpl> CREATOR = new Parcelable.Creator<GroupObjectiveImpl>() {
        public GroupObjectiveImpl createFromParcel(Parcel source) {
            return new GroupObjectiveImpl(source);
        }

        public GroupObjectiveImpl[] newArray(int size) {
            return new GroupObjectiveImpl[size];
        }
    };
    private static final String LINK_DATA_TYPE = "data_type";
    @SerializedName("criteria")
    Criteria criteria;
    transient DataField dataField;
    transient DataType dataType;
    @SerializedName("data_type_field")
    String dataTypeField;
    transient EntityRef<DataType> dataTypeRef;
    @SerializedName("end_datetime")
    Date endDate;
    @SerializedName("iteration")
    Integer iteration;
    @SerializedName("iteration_end_datetime")
    Date iterationEndDate;
    @SerializedName("iteration_start_datetime")
    Date iterationStartDate;
    @SerializedName("name")
    String name;
    @SerializedName("period")
    Period period;
    @SerializedName("start_datetime")
    Date startDate;

    public GroupObjectiveImpl() {
    }

    private GroupObjectiveImpl(Parcel in) {
        super(in);
        this.period = (Period) in.readValue(Iso8601PeriodFormat.class.getClassLoader());
        this.startDate = (Date) in.readValue(Date.class.getClassLoader());
        this.endDate = (Date) in.readValue(Date.class.getClassLoader());
        this.iterationStartDate = (Date) in.readValue(Date.class.getClassLoader());
        this.iterationEndDate = (Date) in.readValue(Date.class.getClassLoader());
        this.iteration = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dataTypeField = in.readString();
        this.name = in.readString();
        this.criteria = (Criteria) in.readParcelable(Criteria.class.getClassLoader());
    }

    public Period getPeriod() {
        return this.period;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Date getIterationStartDate() {
        return this.iterationStartDate;
    }

    public Date getIterationEndDate() {
        return this.iterationEndDate;
    }

    public Integer getIteration() {
        return this.iteration;
    }

    public EntityRef<DataType> getDataTypeRef() {
        Link dataType2;
        if (this.dataTypeRef == null && (dataType2 = getLink(LINK_DATA_TYPE)) != null) {
            this.dataTypeRef = new LinkEntityRef(dataType2.getId(), dataType2.getHref());
        }
        return this.dataTypeRef;
    }

    public DataType getDataType() {
        if (this.dataType == null && getDataTypeRef() != null) {
            this.dataType = BaseDataTypes.ALL_BASE_TYPE_MAP.get(this.dataTypeRef.getId());
        }
        return this.dataType;
    }

    public DataField getDataField() {
        if (this.dataField == null && this.dataTypeField != null) {
            this.dataField = BaseDataTypes.findDataField(this.dataTypeField, getDataType());
        }
        return this.dataField;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setPeriod(Period period2) {
        this.period = period2;
    }

    public void setStartDate(Date startDate2) {
        this.startDate = startDate2;
    }

    public void setEndDate(Date endDate2) {
        this.endDate = endDate2;
    }

    public void setIterationStartDate(Date iterationStartDate2) {
        this.iterationStartDate = iterationStartDate2;
    }

    public void setIterationEndDate(Date iterationEndDate2) {
        this.iterationEndDate = iterationEndDate2;
    }

    public void setIteration(Integer iteration2) {
        this.iteration = iteration2;
    }

    public void setDataField(DataField dataField2) {
        if (dataField2 != null) {
            this.dataField = BaseDataTypes.findDataField(dataField2.getId(), getDataType());
        }
    }

    public void setDataTypeField(String dataTypeField2) {
        this.dataTypeField = dataTypeField2;
    }

    public void setDataTypeRef(EntityRef<DataType> dataTypeRef2) {
        this.dataTypeRef = dataTypeRef2;
        addLink(LINK_DATA_TYPE, new Link(dataTypeRef2.getHref(), dataTypeRef2.getId()));
    }

    public Criteria getCriteria() {
        return this.criteria;
    }

    public void setCriteria(Criteria criteria2) {
        this.criteria = criteria2;
    }

    public EntityRef<GroupObjective> getRef() {
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
        dest.writeValue(this.period);
        dest.writeValue(this.startDate);
        dest.writeValue(this.endDate);
        dest.writeValue(this.iterationStartDate);
        dest.writeValue(this.iterationEndDate);
        dest.writeValue(this.iteration);
        dest.writeString(this.dataTypeField);
        dest.writeString(this.name);
        dest.writeParcelable(this.criteria, flags);
    }
}
