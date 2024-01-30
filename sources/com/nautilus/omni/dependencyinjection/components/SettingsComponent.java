package com.nautilus.omni.dependencyinjection.components;

import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {SettingsModule.class})
public interface SettingsComponent {
    void inject(SettingsActivity settingsActivity);
}
