package com.ua.sdk.page.association;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class PageAssociationRequestJsonWriter extends AbstractGsonWriter<PageAssociation> {
    public PageAssociationRequestJsonWriter() {
        super(GsonFactory.newInstance());
    }

    /* access modifiers changed from: protected */
    public void write(PageAssociation entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) PageAssociationRequestTransferObject.fromPageAssociation(entity), (Appendable) writer);
    }
}
