package com.ua.sdk.recorder;

import com.ua.sdk.EntityRef;
import com.ua.sdk.activitytype.ActivityTypeRef;
import com.ua.sdk.user.User;

public interface RecorderConfiguration {
    RecorderConfiguration addDataSource(DataSourceConfiguration dataSourceConfiguration);

    RecorderConfiguration setActivityTypeRef(ActivityTypeRef activityTypeRef);

    RecorderConfiguration setName(String str);

    RecorderConfiguration setUserRef(EntityRef<User> entityRef);
}
