package com.nautilus.omni.dependencyinjection.modules.settings.MyFitnessPal;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.myfitnesspal.presenter.MyFitnessPalPresenter;
import com.nautilus.omni.appmodules.settings.myfitnesspal.presenter.MyFitnessPalPresenterContract;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class MFPModule {
    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public MyFitnessPalPresenterContract provideMyFitnessPalPresenter(Context context) {
        return new MyFitnessPalPresenter(context);
    }
}
