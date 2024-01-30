package com.nautilus.omni.dependencyinjection.components;

import com.nautilus.omni.appmodules.settings.googlefit.view.ConfigGoogleFitActivity;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitActivity;
import com.nautilus.omni.dependencyinjection.modules.settings.googlefit.GoogleFitModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {GoogleFitModule.class})
public interface GoogleFitComponent {
    void inject(ConfigGoogleFitActivity configGoogleFitActivity);

    void inject(ConnectionGoogleFitActivity connectionGoogleFitActivity);
}
