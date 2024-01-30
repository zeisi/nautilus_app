package com.nautilus.omni.dependencyinjection.components;

import com.nautilus.omni.appmodules.splash.view.SplashActivity;
import com.nautilus.omni.dependencyinjection.modules.splash.SplashModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {SplashModule.class})
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
