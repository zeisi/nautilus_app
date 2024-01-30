package com.ua.sdk.workout;

import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class WorkoutService extends AbstractResourceService<Workout> {
    public WorkoutService(ConnectionFactory connectionFactory, AuthenticationManager authenticationManager, UrlBuilder urlBuilder, JsonParser<Workout> jsonParser, JsonWriter<Workout> jsonWriter, JsonParser<WorkoutList> jsonPagedParser) {
        super(connectionFactory, authenticationManager, urlBuilder, jsonParser, jsonWriter, jsonPagedParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateWorkoutUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetWorkoutByIdUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(Workout resource) {
        return this.urlBuilder.buildSaveWorkoutUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteWorkoutUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        return null;
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<Workout> ref) {
        return this.urlBuilder.buildGetWorkoutsListUrl(ref);
    }
}
