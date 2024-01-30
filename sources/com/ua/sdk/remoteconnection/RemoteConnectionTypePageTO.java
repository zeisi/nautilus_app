package com.ua.sdk.remoteconnection;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoteConnectionTypePageTO extends ApiTransferObject {
    public static final String KEY_REMOTE_CONNECTION_TYPE = "remoteconnectiontypes";
    @SerializedName("_embedded")
    Map<String, ArrayList<RemoteConnectionTypeTransferObject>> remoteConnectionTypes;
    @SerializedName("total_count")
    Integer totalCount;

    private List<RemoteConnectionTypeTransferObject> getRemoteConnectionTypeList() {
        if (this.remoteConnectionTypes == null) {
            return null;
        }
        return this.remoteConnectionTypes.get(KEY_REMOTE_CONNECTION_TYPE);
    }

    public static RemoteConnectionTypeListImpl fromTransferObject(RemoteConnectionTypePageTO to) {
        RemoteConnectionTypeListImpl page = new RemoteConnectionTypeListImpl();
        for (RemoteConnectionTypeTransferObject transferObject : to.getRemoteConnectionTypeList()) {
            try {
                page.add(RemoteConnectionTypeTransferObject.toRemoteConnectionTypeImpl(transferObject));
            } catch (UaException e) {
                UaLog.error("Error converting RemoteConnectionTypeTransferObject to RemoteConnectionTypeImpl.", (Throwable) e);
            }
        }
        page.setLinkMap(to.getLinkMap());
        page.setTotalCount(to.totalCount.intValue());
        return page;
    }
}
