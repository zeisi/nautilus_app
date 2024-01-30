package com.ua.sdk.page;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PagePageTO extends ApiTransferObject {
    public static final String KEY_PAGE = "pages";
    @SerializedName("_embedded")
    public Map<String, ArrayList<PageTO>> pages;
    @SerializedName("total_count")
    public Integer totalPagesCount;

    private ArrayList<PageTO> getPageList() {
        if (this.pages == null) {
            return null;
        }
        return this.pages.get("pages");
    }

    public static PageListImpl toPage(PagePageTO to) {
        PageListImpl page = new PageListImpl();
        Iterator i$ = to.getPageList().iterator();
        while (i$.hasNext()) {
            try {
                page.add(PageTO.toPageImpl(i$.next()));
            } catch (UaException e) {
                UaLog.error("Error converting PageTO to PageImpl.", (Throwable) e);
            }
        }
        page.setLinkMap(to.getLinkMap());
        page.setTotalCount(to.totalPagesCount.intValue());
        return page;
    }
}
