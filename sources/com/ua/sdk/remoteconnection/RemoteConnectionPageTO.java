package com.ua.sdk.remoteconnection;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RemoteConnectionPageTO extends ApiTransferObject {
    public static final String KEY_REMOTE_CONNECTIONS = "remoteconnections";
    @SerializedName("_embedded")
    public Map<String, ArrayList<RemoteConnectionTransferObject>> remoteConnections;
    @SerializedName("total_count")
    public Integer totalRemoteConnectionsCount;

    private ArrayList<RemoteConnectionTransferObject> getRemoteConnectionList() {
        if (this.remoteConnections == null) {
            return null;
        }
        return this.remoteConnections.get(KEY_REMOTE_CONNECTIONS);
    }

    public static RemoteConnectionPageTO fromPage(EntityList<RemoteConnection> remoteConnectionPage) {
        if (remoteConnectionPage == null) {
            return null;
        }
        RemoteConnectionPageTO remoteConnectionPageTO = new RemoteConnectionPageTO();
        for (RemoteConnection remoteConnection : remoteConnectionPage.getAll()) {
            remoteConnectionPageTO.getRemoteConnectionList().add(RemoteConnectionTransferObject.fromRemoteConnection(remoteConnection));
        }
        if (remoteConnectionPage instanceof RemoteConnectionListImpl) {
            remoteConnectionPageTO.setLinkMap(((RemoteConnectionListImpl) remoteConnectionPage).getLinkMap());
        }
        remoteConnectionPageTO.totalRemoteConnectionsCount = Integer.valueOf(remoteConnectionPage.getTotalCount());
        return remoteConnectionPageTO;
    }

    public static RemoteConnectionListImpl toPage(RemoteConnectionPageTO to) {
        RemoteConnectionListImpl page = new RemoteConnectionListImpl();
        Iterator i$ = to.getRemoteConnectionList().iterator();
        while (i$.hasNext()) {
            try {
                page.add(RemoteConnectionTransferObject.toRemoteConnectionImpl(i$.next()));
            } catch (UaException e) {
                UaLog.error("Error converting RemoteConnectionTransferObject to RemoteConnectionImpl.", (Throwable) e);
            }
        }
        page.setLinkMap(to.getLinkMap());
        page.setTotalCount(to.totalRemoteConnectionsCount.intValue());
        return page;
    }
}
