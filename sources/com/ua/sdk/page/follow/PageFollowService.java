package com.ua.sdk.page.follow;

import com.android.volley.toolbox.HttpClientStack;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.io.InterruptedIOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class PageFollowService extends AbstractResourceService<PageFollow> {
    public PageFollowService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<PageFollow> pageFollowParser, JsonParser<EntityList<PageFollow>> pageFollowPageParser, JsonWriter<PageFollow> pageFollowRequestWriter) {
        super(connFactory, authManager, urlBuilder, pageFollowParser, pageFollowRequestWriter, pageFollowPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreatePageFollowUrl();
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(PageFollow p) {
        throw new UnsupportedOperationException("Update PageFollow is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeletePageFollowUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetPageFollowUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<PageFollow> ref) {
        return this.urlBuilder.buildGetPageFollowPageUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        return this.urlBuilder.buildPatchPageFollowUrl(ref);
    }

    /* access modifiers changed from: protected */
    public EntityList<PageFollow> patch(PageFollow follow) throws UaException {
        HttpsURLConnection conn;
        Precondition.isNotNull(this.jsonWriter, "jsonWriter");
        Precondition.isNotNull(this.jsonPageParser, "jsonPageParser");
        try {
            conn = this.connFactory.getSslConnection(getPatchUrl((Reference) null));
            this.authManager.sign(conn, getPatchAuthenticationType());
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.addRequestProperty("X-HTTP-Method-Override", HttpClientStack.HttpPatch.METHOD_NAME);
            this.jsonWriter.write(follow, conn.getOutputStream());
            Precondition.isResponseSuccess(conn);
            EntityList<PageFollow> entityList = (EntityList) this.jsonPageParser.parse(conn.getInputStream());
            conn.disconnect();
            return entityList;
        } catch (InterruptedIOException e) {
            UaLog.debug("Multi patch cancelled.");
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (Throwable t) {
            UaLog.error("Unable to multi patch page follows.", t);
            throw new UaException("Unable to multi patch page follows.", t);
        }
    }
}
