package com.ua.sdk.activitystory;

import com.nautilus.omni.bleservices.ble.omnitrainer.OmniDictionaryKeys;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;
import java.util.concurrent.Callable;
import javax.net.ssl.HttpsURLConnection;

public class ActivityStoryService extends AbstractResourceService<ActivityStory> {
    public ActivityStoryService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<? extends EntityList<ActivityStory>> activityStoryPageParser, JsonParser<ActivityStory> activityStoryParser, JsonWriter<ActivityStory> activityStoryWriter) {
        super(connFactory, authManager, urlBuilder, activityStoryParser, activityStoryWriter, activityStoryPageParser);
    }

    /* access modifiers changed from: protected */
    public Callable<ActivityStory> getSaveCallable(final ActivityStory resource) throws UaException {
        return new Callable<ActivityStory>() {
            public ActivityStory call() throws Exception {
                HttpsURLConnection conn = ActivityStoryService.this.connFactory.getSslConnection(ActivityStoryService.this.getSaveUrl(resource));
                try {
                    ActivityStoryService.this.authManager.sign(conn, ActivityStoryService.this.getSaveAuthenticationType());
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    ActivityStoryService.this.jsonWriter.write(resource, conn.getOutputStream());
                    Precondition.isExpectedResponse(conn, OmniDictionaryKeys.ProductFWVersion);
                    return resource;
                } finally {
                    conn.disconnect();
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(ActivityStory resource) {
        return this.urlBuilder.buildRpcPatchActivityStoryUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildGetActivityStoryUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateActivityStoryUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<ActivityStory> ref) {
        return this.urlBuilder.buildGetActivityFeedUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetActivityStoryUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        return this.urlBuilder.buildGetActivityStoryUrl(ref);
    }
}
