package com.ua.sdk.recorder;

import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;

public interface DataSourceConfiguration<T> {
    T setDataSourceIdentifier(DataSourceIdentifier dataSourceIdentifier);
}
