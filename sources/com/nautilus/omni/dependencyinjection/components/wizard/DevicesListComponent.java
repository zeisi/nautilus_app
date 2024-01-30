package com.nautilus.omni.dependencyinjection.components.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivity;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.DevicesListModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {WizardDataModule.class, DevicesListModule.class})
public interface DevicesListComponent {
    void inject(OmniTrainerListActivity omniTrainerListActivity);
}
