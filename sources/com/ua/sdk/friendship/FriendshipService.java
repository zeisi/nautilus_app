package com.ua.sdk.friendship;

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

public class FriendshipService extends AbstractResourceService<Friendship> {
    public FriendshipService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<EntityList<Friendship>> friendshipPageParser, JsonParser<Friendship> friendshipParser, JsonWriter<Friendship> friendshipWriter) {
        super(connFactory, authManager, urlBuilder, friendshipParser, friendshipWriter, friendshipPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(Friendship resource) {
        return this.urlBuilder.buildApproveFriendship(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteFriendshipUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildSendFriendshipRequest();
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<Friendship> ref) {
        return this.urlBuilder.buildGetFriendsUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException("Fetch Friendship is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        return this.urlBuilder.buildPatchFriendshipRequest(ref);
    }

    /* access modifiers changed from: protected */
    public EntityList<Friendship> patch(Friendship friendship) throws UaException {
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
            this.jsonWriter.write(friendship, conn.getOutputStream());
            Precondition.isResponseSuccess(conn);
            EntityList<Friendship> entityList = (EntityList) this.jsonPageParser.parse(conn.getInputStream());
            conn.disconnect();
            return entityList;
        } catch (InterruptedIOException e) {
            UaLog.debug("Multi patch cancelled.");
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (Throwable t) {
            UaLog.error("Unable to multi patch friendships.", t);
            throw new UaException("Unable to multi patch friendships.", t);
        }
    }
}
