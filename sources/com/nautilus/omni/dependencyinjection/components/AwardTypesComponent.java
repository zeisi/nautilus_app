package com.nautilus.omni.dependencyinjection.components;

import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {AwardTypesModule.class})
public interface AwardTypesComponent {
    void inject(AwardTypesActivity awardTypesActivity);
}
