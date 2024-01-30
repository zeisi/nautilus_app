package com.ua.sdk.recorder;

import com.ua.sdk.EntityRef;
import com.ua.sdk.activitytype.ActivityTypeRef;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.List;

public class RecorderConfigurationImpl implements RecorderConfiguration {
    private ActivityTypeRef activityTypeRef;
    private List<DataSourceConfiguration> dataSourceConfigurations;
    private String name;
    private EntityRef<User> userRef;

    public String getName() {
        return this.name;
    }

    public RecorderConfigurationImpl setName(String name2) {
        this.name = name2;
        return this;
    }

    public EntityRef<User> getUserRef() {
        return this.userRef;
    }

    public RecorderConfigurationImpl setUserRef(EntityRef<User> userRef2) {
        this.userRef = userRef2;
        return this;
    }

    public ActivityTypeRef getActivityTypeRef() {
        return this.activityTypeRef;
    }

    public RecorderConfigurationImpl setActivityTypeRef(ActivityTypeRef activityTypeRef2) {
        this.activityTypeRef = activityTypeRef2;
        return this;
    }

    public RecorderConfigurationImpl addDataSource(DataSourceConfiguration dataSourceConfiguration) {
        if (this.dataSourceConfigurations == null) {
            this.dataSourceConfigurations = new ArrayList();
        }
        this.dataSourceConfigurations.add(dataSourceConfiguration);
        return this;
    }

    public List<DataSourceConfiguration> getDataSourceConfigurations() {
        return this.dataSourceConfigurations;
    }
}
