package com.ua.sdk.remoteconnection;

import com.ua.sdk.Entity;
import java.util.Date;

public interface RemoteConnection extends Entity {
    Date getCreatedDateTime();

    Date getLastSyncTime();

    String getType();

    void setCreatedDateTime(Date date);

    void setLastSyncTime(Date date);

    void setType(String str);
}
