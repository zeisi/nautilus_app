package com.ua.sdk.route;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RoutePageTO extends ApiTransferObject {
    public static final String KEY_ROUTES = "routes";
    @SerializedName("_embedded")
    public Map<String, ArrayList<RouteTO>> routes;
    @SerializedName("total_count")
    public Integer totalRoutesCount;

    private ArrayList<RouteTO> getRoutesList() {
        if (this.routes == null) {
            return null;
        }
        return this.routes.get("routes");
    }

    public static RouteList fromTransferObject(RoutePageTO to) {
        RouteList page = new RouteList();
        Iterator i$ = to.getRoutesList().iterator();
        while (i$.hasNext()) {
            page.add(RouteTO.fromTransferObject(i$.next()));
        }
        page.setLinkMap(to.getLinkMap());
        page.setTotalCount(to.totalRoutesCount.intValue());
        return page;
    }
}
