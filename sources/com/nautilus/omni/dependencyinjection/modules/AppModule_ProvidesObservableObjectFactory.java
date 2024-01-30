package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.syncservices.ObservableObject;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvidesObservableObjectFactory implements Factory<ObservableObject> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AppModule_ProvidesObservableObjectFactory.class.desiredAssertionStatus());
    private final AppModule module;

    public AppModule_ProvidesObservableObjectFactory(AppModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public ObservableObject get() {
        return (ObservableObject) Preconditions.checkNotNull(this.module.providesObservableObject(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ObservableObject> create(AppModule module2) {
        return new AppModule_ProvidesObservableObjectFactory(module2);
    }

    public static ObservableObject proxyProvidesObservableObject(AppModule instance) {
        return instance.providesObservableObject();
    }
}
