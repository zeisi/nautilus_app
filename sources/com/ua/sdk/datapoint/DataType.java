package com.ua.sdk.datapoint;

import com.ua.sdk.Entity;
import java.util.List;

public interface DataType extends Entity<DataTypeRef> {
    String getDescription();

    List<DataField> getFields();

    String getId();

    DataPeriod getPeriod();
}
