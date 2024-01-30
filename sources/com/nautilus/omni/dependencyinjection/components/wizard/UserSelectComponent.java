package com.nautilus.omni.dependencyinjection.components.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivity;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.UserSelectModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {WizardDataModule.class, UserSelectModule.class})
public interface UserSelectComponent {
    void inject(SelectUserNumberActivity selectUserNumberActivity);
}
