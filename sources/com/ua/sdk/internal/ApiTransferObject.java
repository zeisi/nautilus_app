package com.ua.sdk.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.util.Utility;
import com.ua.sdk.util.Validate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApiTransferObject {
    public static final String LINK_SELF = "self";
    @SerializedName("_links")
    Map<String, ArrayList<Link>> mLinkMap;
    protected transient long mLocalId = -1;

    public ApiTransferObject() {
    }

    protected ApiTransferObject(Parcel in) {
        Bundle bundle = in.readBundle(Link.class.getClassLoader());
        this.mLinkMap = new HashMap(0);
        for (String key : bundle.keySet()) {
            this.mLinkMap.put(key, bundle.getParcelableArrayList(key));
        }
        if (this.mLinkMap.isEmpty()) {
            this.mLinkMap = null;
        }
        this.mLocalId = in.readLong();
    }

    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle;
        if (this.mLinkMap == null) {
            bundle = new Bundle(0);
        } else {
            bundle = new Bundle(this.mLinkMap.size());
            for (Map.Entry<String, ArrayList<Link>> entry : this.mLinkMap.entrySet()) {
                bundle.putParcelableArrayList(entry.getKey(), entry.getValue());
            }
        }
        dest.writeBundle(bundle);
        dest.writeLong(this.mLocalId);
    }

    public void setLocalId(long localId) {
        this.mLocalId = localId;
    }

    public long getLocalId() {
        return this.mLocalId;
    }

    /* access modifiers changed from: protected */
    public String getHref() {
        Link selfLink = getLink("self");
        if (selfLink == null) {
            return null;
        }
        return selfLink.getHref();
    }

    public void setLinkMap(Map<String, ArrayList<Link>> links) {
        if (links == null || links.isEmpty()) {
            this.mLinkMap = null;
        } else {
            this.mLinkMap = links;
        }
    }

    public Map<String, ArrayList<Link>> getLinkMap() {
        return this.mLinkMap;
    }

    public void copyLinkMap(Map<String, ArrayList<Link>> linkMap) {
        if (linkMap == null) {
            this.mLinkMap = null;
            return;
        }
        Map<String, ArrayList<Link>> newLinkMap = new HashMap<>(linkMap.size());
        for (Map.Entry<String, ArrayList<Link>> entry : linkMap.entrySet()) {
            newLinkMap.put(entry.getKey(), new ArrayList(entry.getValue()));
        }
        this.mLinkMap = newLinkMap;
    }

    public void setLinksForRelation(String relation, ArrayList<Link> links) {
        if (this.mLinkMap == null) {
            this.mLinkMap = new HashMap(1);
        }
        this.mLinkMap.put(relation, links);
    }

    public void addLink(String key, Link link) {
        if (this.mLinkMap == null) {
            this.mLinkMap = new HashMap();
        }
        ArrayList<Link> links = this.mLinkMap.get(key);
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
        this.mLinkMap.put(key, links);
    }

    public void setLink(String key, Link link) {
        if (this.mLinkMap == null) {
            this.mLinkMap = new HashMap();
        }
        ArrayList<Link> links = this.mLinkMap.get(key);
        if (links == null) {
            links = new ArrayList<>(1);
        } else {
            links.clear();
        }
        links.add(link);
        this.mLinkMap.put(key, links);
    }

    public ArrayList<Link> getLinks(String key) {
        if (this.mLinkMap == null || key == null) {
            return null;
        }
        return this.mLinkMap.get(key);
    }

    public Link getLink(String key) {
        Validate.notNull(key, "key");
        return getLink(key, 0);
    }

    public Link getLink(String key, String name) {
        List<Link> list = getLinks(key);
        if (list != null) {
            for (Link l : list) {
                if (Utility.isEqual(name, l.getName(), true)) {
                    return l;
                }
            }
        }
        return null;
    }

    public Link getLink(String key, int index) {
        List<Link> list = getLinks(key);
        if (list == null || list.size() <= index) {
            return null;
        }
        return list.get(index);
    }

    public Set<String> getLinkKeys() {
        if (this.mLinkMap == null) {
            return Collections.emptySet();
        }
        return this.mLinkMap.keySet();
    }
}
