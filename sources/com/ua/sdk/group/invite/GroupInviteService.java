package com.ua.sdk.group.invite;

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

public class GroupInviteService extends AbstractResourceService<GroupInvite> {
    public GroupInviteService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<GroupInvite> jsonParser, JsonWriter<GroupInvite> jsonWriter, JsonParser<? extends EntityList<GroupInvite>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateGroupInviteUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(GroupInvite resource) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteGroupInviteUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        return this.urlBuilder.buildPatchGroupInviteUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<GroupInvite> ref) {
        return this.urlBuilder.buildGetGroupInviteUrl(ref);
    }

    /* access modifiers changed from: protected */
    public EntityList<GroupInvite> patch(GroupInvite invite) throws UaException {
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
            this.jsonWriter.write(invite, conn.getOutputStream());
            Precondition.isResponseSuccess(conn);
            EntityList<GroupInvite> entityList = (EntityList) this.jsonPageParser.parse(conn.getInputStream());
            conn.disconnect();
            return entityList;
        } catch (InterruptedIOException e) {
            UaLog.debug("Multi patch cancelled.");
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (Throwable t) {
            UaLog.error("Unable to multi patch group invites.", t);
            throw new UaException("Unable to multi patch group invites.", t);
        }
    }
}
