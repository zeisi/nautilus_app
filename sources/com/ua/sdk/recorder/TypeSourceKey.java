package com.ua.sdk.recorder;

import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.internal.Precondition;

public class TypeSourceKey {
    private static final String DELIMITER = "|&|";

    public static String createDataPointKey(DataTypeRef dataTypeRef, DataSourceIdentifier dataSourceIdentifier) {
        Precondition.isNotNull(dataTypeRef, "DataTypeRef");
        Precondition.isNotNull(dataSourceIdentifier, "DataSourceIdentifier");
        return dataTypeRef.getId() + DELIMITER + dataSourceIdentifier.getName();
    }
}
