package com.nautilus.omni.dependencyinjection.components;

import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {WorkoutDetailModule.class})
public interface WorkoutDetailComponent {
    void inject(WorkoutDetailActivity workoutDetailActivity);
}
