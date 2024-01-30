package com.ua.sdk.remoteconnection;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.Date;

public class RemoteConnectionTransferObject extends ApiTransferObject {
    @SerializedName("created_datetime")
    Date createdDatetime;
    @SerializedName("last_sync_time")
    Date lastSyncTime;
    @SerializedName("type")
    String type;

    public static RemoteConnectionTransferObject fromRemoteConnection(RemoteConnection rc) {
        if (rc == null) {
            return null;
        }
        RemoteConnectionTransferObject to = new RemoteConnectionTransferObject();
        to.createdDatetime = rc.getCreatedDateTime();
        to.lastSyncTime = rc.getLastSyncTime();
        to.type = rc.getType();
        if (!(rc instanceof RemoteConnectionImpl)) {
            return to;
        }
        to.setLinkMap(((RemoteConnectionImpl) rc).getLinkMap());
        return to;
    }

    public static RemoteConnectionImpl toRemoteConnectionImpl(RemoteConnectionTransferObject obj) throws UaException {
        if (obj == null) {
            return null;
        }
        RemoteConnectionImpl rci = new RemoteConnectionImpl();
        rci.setCreatedDateTime(obj.createdDatetime);
        rci.setLastSyncTime(obj.lastSyncTime);
        rci.setType(obj.type);
        for (String key : obj.getLinkKeys()) {
            rci.setLinksForRelation(key, obj.getLinks(key));
        }
        return rci;
    }
}
