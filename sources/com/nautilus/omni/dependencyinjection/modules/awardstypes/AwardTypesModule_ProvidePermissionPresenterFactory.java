package com.nautilus.omni.dependencyinjection.modules.awardstypes;

import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AwardTypesModule_ProvidePermissionPresenterFactory implements Factory<PermissionPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesModule_ProvidePermissionPresenterFactory.class.desiredAssertionStatus());
    private final Provider<AwardTypesActivity> awardTypesActivityProvider;
    private final AwardTypesModule module;
    private final Provider<PermissionAction> permissionActionProvider;

    public AwardTypesModule_ProvidePermissionPresenterFactory(AwardTypesModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<AwardTypesActivity> awardTypesActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || permissionActionProvider2 != null) {
                this.permissionActionProvider = permissionActionProvider2;
                if ($assertionsDisabled || awardTypesActivityProvider2 != null) {
                    this.awardTypesActivityProvider = awardTypesActivityProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionPresenter get() {
        return (PermissionPresenter) Preconditions.checkNotNull(this.module.providePermissionPresenter(this.permissionActionProvider.get(), this.awardTypesActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionPresenter> create(AwardTypesModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<AwardTypesActivity> awardTypesActivityProvider2) {
        return new AwardTypesModule_ProvidePermissionPresenterFactory(module2, permissionActionProvider2, awardTypesActivityProvider2);
    }

    public static PermissionPresenter proxyProvidePermissionPresenter(AwardTypesModule instance, PermissionAction permissionAction, AwardTypesActivity awardTypesActivity) {
        return instance.providePermissionPresenter(permissionAction, awardTypesActivity);
    }
}
