package com.nautilus.omni.dependencyinjection.modules.awardstypes;

import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity;
import com.nautilus.omni.permissions.PermissionAction;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AwardTypesModule_ProvidePermissionActionFactory implements Factory<PermissionAction> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesModule_ProvidePermissionActionFactory.class.desiredAssertionStatus());
    private final Provider<AwardTypesActivity> awardTypesActivityProvider;
    private final AwardTypesModule module;

    public AwardTypesModule_ProvidePermissionActionFactory(AwardTypesModule module2, Provider<AwardTypesActivity> awardTypesActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || awardTypesActivityProvider2 != null) {
                this.awardTypesActivityProvider = awardTypesActivityProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionAction get() {
        return (PermissionAction) Preconditions.checkNotNull(this.module.providePermissionAction(this.awardTypesActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionAction> create(AwardTypesModule module2, Provider<AwardTypesActivity> awardTypesActivityProvider2) {
        return new AwardTypesModule_ProvidePermissionActionFactory(module2, awardTypesActivityProvider2);
    }

    public static PermissionAction proxyProvidePermissionAction(AwardTypesModule instance, AwardTypesActivity awardTypesActivity) {
        return instance.providePermissionAction(awardTypesActivity);
    }
}
