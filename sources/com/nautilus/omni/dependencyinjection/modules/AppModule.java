package com.nautilus.omni.dependencyinjection.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelper;
import com.nautilus.omni.syncservices.ObservableObject;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public Application providesApplicationContext() {
        return this.mApplication;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return this.mApplication;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public ObservableObject providesObservableObject() {
        return new ObservableObject();
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public LocationSettingsUtil provideLocationSettingsUtil() {
        return new LocationSettingsUtil();
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public GoogleFitHelper provideGoogleFitHelper(Context context) {
        return new GoogleFitHelper(context);
    }
}
