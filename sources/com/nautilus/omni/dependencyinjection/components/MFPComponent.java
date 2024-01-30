package com.nautilus.omni.dependencyinjection.components;

import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalConnectionActivity;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalDisconnectionActivity;
import com.nautilus.omni.dependencyinjection.modules.settings.MyFitnessPal.MFPModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {MFPModule.class})
public interface MFPComponent {
    void inject(MyFitnessPalConnectionActivity myFitnessPalConnectionActivity);

    void inject(MyFitnessPalDisconnectionActivity myFitnessPalDisconnectionActivity);
}
