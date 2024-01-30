package com.ua.sdk.remoteconnection;

import com.nautilus.underarmourconnection.services.workouts.ActivityType;
import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.actigraphysettings.ActigraphySettings;
import com.ua.sdk.internal.Link;
import java.util.ArrayList;

public class RemoteConnectionTypeManagerDummyImpl implements RemoteConnectionTypeManager {
    private RemoteConnectionTypePageImpl mRemoteConnectionTypePage;

    public RemoteConnectionTypePageImpl fetchRemoteConnectionTypeList() throws UaException {
        if (this.mRemoteConnectionTypePage == null) {
            this.mRemoteConnectionTypePage = new RemoteConnectionTypePageImpl();
            this.mRemoteConnectionTypePage.add(createRemoteConnectionType(1));
            this.mRemoteConnectionTypePage.add(createRemoteConnectionType(2));
            this.mRemoteConnectionTypePage.add(createRemoteConnectionType(3));
        }
        return this.mRemoteConnectionTypePage;
    }

    public Request fetchRemoteConnectionTypeList(FetchCallback<EntityList<RemoteConnectionType>> fetchCallback) {
        return null;
    }

    public RemoteConnectionType fetchRemoteConnectionType(EntityRef<RemoteConnectionType> ref) throws UaException {
        return createRemoteConnectionType(Integer.valueOf(ref.getId()).intValue());
    }

    public Request fetchRemoteConnectionType(EntityRef<RemoteConnectionType> entityRef, FetchCallback<RemoteConnectionType> fetchCallback) {
        return null;
    }

    public ActigraphySettings fetchRemoteConnectionPriorities() throws UaException {
        return null;
    }

    public Request fetchRemoteConnectionPriorities(FetchCallback<ActigraphySettings> fetchCallback) {
        return null;
    }

    public void updateSleepConnectionPriorities(EntityRef<RemoteConnectionType> entityRef) throws UaException {
    }

    public Request updateSleepConnectionPriorities(EntityRef<RemoteConnectionType> entityRef, CreateCallback<ActigraphySettings> createCallback) {
        return null;
    }

    public void updateActivityConnectionPriorities(EntityRef<RemoteConnectionType> entityRef) throws UaException {
    }

    public Request updateActivityConnectionPriorities(EntityRef<RemoteConnectionType> entityRef, CreateCallback<ActigraphySettings> createCallback) {
        return null;
    }

    public RemoteConnectionTypeImpl createRemoteConnectionType(int id) {
        RemoteConnectionTypeImpl rc = new RemoteConnectionTypeImpl();
        ArrayList<Link> links = new ArrayList<>();
        switch (id) {
            case 1:
                rc.setRecorderTypeKey("fitbit");
                rc.setDisconnectCancelStr("No, keep it linked");
                rc.setName("Fitbit");
                rc.setLogoLink("http://d2i3r43q6ffvz8.cloudfront.net/prod/hashed/8fe4f617a43cf64cc9d1a76f38254e42394b12d9.png");
                rc.setOAuthLink("/remote/connect/?remote_code=fitbit");
                rc.setDisconnectConfirmStr("Yes, I'm sure");
                rc.setDisconnectStr("Are you sure you want to disconnect your Fitbit account from MapMyFitness?");
                rc.setIntroHeadingStr("To get started, log into your account.");
                rc.setType("fitbit");
                rc.setIntroBodyStr("On initial registration, 1 month of your Fitbit history will be imported. Subsequent updates will automatically occur whenever you sync your device with your Fitbit account as normal.");
                links.add(new Link("/vx/remoteconnectiontype/1/", ActivityType.GENERIC));
                rc.setLinksForRelation("self", links);
                break;
            case 2:
                rc.setRecorderTypeKey("jawboneupmoves");
                rc.setDisconnectCancelStr("No, keep it linked");
                rc.setName("Jawbone");
                rc.setLogoLink("http://d2i3r43q6ffvz8.cloudfront.net/prod/hashed/65db9f887364d4d031b004656c519bc11055b5f2.png");
                rc.setOAuthLink("/remote/connect/?remote_code=jawboneup");
                rc.setDisconnectConfirmStr("Yes, I'm sure");
                rc.setDisconnectStr("Are you sure you want to disconnect your Jawbone account from MapMyFitness?");
                rc.setIntroHeadingStr("To get started, log into your account.");
                rc.setType("jawboneup");
                rc.setIntroBodyStr("On initial registration, 1 month of your Jawbone history will be imported. Subsequent updates will automatically occur whenever you sync your device with your Jawbone account as normal.");
                links.add(new Link("/vx/remoteconnectiontype/2/", "2"));
                rc.setLinksForRelation("self", links);
                break;
            default:
                rc.setRecorderTypeKey("withings");
                rc.setDisconnectCancelStr("No, keep it linked");
                rc.setName("Withings");
                rc.setLogoLink("http://d2i3r43q6ffvz8.cloudfront.net/prod/hashed/1f4ee8da8a4f54872e14abaad2632721827695fb.png");
                rc.setOAuthLink("/remote/connect/?remote_code=withings");
                rc.setDisconnectConfirmStr("Yes, I'm sure");
                rc.setDisconnectStr("Are you sure you want to disconnect your Withings account from MapMyFitness?");
                rc.setIntroHeadingStr("To get started, log into your account.");
                rc.setType("withings");
                rc.setIntroBodyStr("On initial registration, 1 month of your Withings history will be imported. Subsequent updates will automatically occur whenever you sync your device with your Withings account as normal.");
                links.add(new Link("/vx/remoteconnectiontype/3/", "3"));
                rc.setLinksForRelation("self", links);
                break;
        }
        return rc;
    }
}
