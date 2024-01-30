package com.nautilus.omni.dependencyinjection.modules.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivityContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class UserSelectModule_ProvideISelectUserNumberActivityFactory implements Factory<SelectUserNumberActivityContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UserSelectModule_ProvideISelectUserNumberActivityFactory.class.desiredAssertionStatus());
    private final UserSelectModule module;

    public UserSelectModule_ProvideISelectUserNumberActivityFactory(UserSelectModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public SelectUserNumberActivityContract get() {
        return (SelectUserNumberActivityContract) Preconditions.checkNotNull(this.module.provideISelectUserNumberActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SelectUserNumberActivityContract> create(UserSelectModule module2) {
        return new UserSelectModule_ProvideISelectUserNumberActivityFactory(module2);
    }

    public static SelectUserNumberActivityContract proxyProvideISelectUserNumberActivity(UserSelectModule instance) {
        return instance.provideISelectUserNumberActivity();
    }
}
