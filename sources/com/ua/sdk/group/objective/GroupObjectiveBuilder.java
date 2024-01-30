package com.ua.sdk.group.objective;

import com.ua.sdk.datapoint.DataField;
import com.ua.sdk.datapoint.DataType;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.internal.Period;
import com.ua.sdk.internal.Precondition;
import java.util.Date;

public class GroupObjectiveBuilder {
    Criteria criteria;
    String dataTypeField;
    DataTypeRef dataTypeRef;
    Date endDate;
    int iteration;
    Date iterationEndDate;
    Date iterationStartDate;
    String name;
    Period period;
    Date startDate;

    public GroupObjectiveBuilder setStartDate(Date startDate2) {
        this.startDate = startDate2;
        return this;
    }

    public GroupObjectiveBuilder setEndDate(Date endDate2) {
        this.endDate = endDate2;
        return this;
    }

    public GroupObjectiveBuilder setIterationStartDate(Date iterationStartDate2) {
        this.iterationStartDate = iterationStartDate2;
        return this;
    }

    public GroupObjectiveBuilder setIterationEndDate(Date iterationEndDate2) {
        this.iterationEndDate = iterationEndDate2;
        return this;
    }

    public GroupObjectiveBuilder setIteration(int iteration2) {
        this.iteration = iteration2;
        return this;
    }

    public GroupObjectiveBuilder setPeriod(Period period2) {
        this.period = period2;
        return this;
    }

    public GroupObjectiveBuilder setDataType(DataType dataType) {
        Precondition.isNotNull(dataType);
        this.dataTypeRef = (DataTypeRef) dataType.getRef();
        return this;
    }

    public GroupObjectiveBuilder setDataField(DataField dataField) {
        Precondition.isNotNull(dataField);
        this.dataTypeField = dataField.getId();
        return this;
    }

    public GroupObjectiveBuilder setName(String name2) {
        this.name = name2;
        return this;
    }

    public GroupObjectiveBuilder setCriteria(Criteria criteria2) {
        this.criteria = criteria2;
        return this;
    }

    public GroupObjective build() {
        Precondition.isNotNull(this.dataTypeRef, "dataTypeRef");
        Precondition.isNotNull(this.dataTypeField, "dataTypeField");
        if (this.startDate == null && this.endDate == null) {
            Precondition.isNotNull(this.period);
        }
        if (this.period == null) {
            Precondition.isNotNull(this.startDate);
            Precondition.isNotNull(this.endDate);
        }
        GroupObjectiveImpl impl = new GroupObjectiveImpl();
        impl.setPeriod(this.period);
        impl.setStartDate(this.startDate);
        impl.setEndDate(this.endDate);
        impl.setIterationStartDate(this.iterationStartDate);
        impl.setIterationEndDate(this.iterationEndDate);
        impl.setIteration(Integer.valueOf(this.iteration));
        impl.setDataTypeField(this.dataTypeField);
        impl.setDataTypeRef(this.dataTypeRef);
        impl.setName(this.name);
        impl.setCriteria(this.criteria);
        return impl;
    }
}
