package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.permissions.PermissionAction;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class MainModule_ProvidePermissionActionFactory implements Factory<PermissionAction> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainModule_ProvidePermissionActionFactory.class.desiredAssertionStatus());
    private final Provider<MainActivity> mainActivityProvider;
    private final MainModule module;

    public MainModule_ProvidePermissionActionFactory(MainModule module2, Provider<MainActivity> mainActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || mainActivityProvider2 != null) {
                this.mainActivityProvider = mainActivityProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionAction get() {
        return (PermissionAction) Preconditions.checkNotNull(this.module.providePermissionAction(this.mainActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionAction> create(MainModule module2, Provider<MainActivity> mainActivityProvider2) {
        return new MainModule_ProvidePermissionActionFactory(module2, mainActivityProvider2);
    }

    public static PermissionAction proxyProvidePermissionAction(MainModule instance, MainActivity mainActivity) {
        return instance.providePermissionAction(mainActivity);
    }
}
