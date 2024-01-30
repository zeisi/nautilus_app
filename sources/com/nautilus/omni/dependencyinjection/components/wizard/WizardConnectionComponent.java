package com.nautilus.omni.dependencyinjection.components.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.ConnectionModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {WizardDataModule.class, ConnectionModule.class})
public interface WizardConnectionComponent {
    void inject(ConnectionWizardActivity connectionWizardActivity);
}
