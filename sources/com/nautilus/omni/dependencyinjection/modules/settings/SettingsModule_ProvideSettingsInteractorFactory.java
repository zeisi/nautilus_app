package com.nautilus.omni.dependencyinjection.modules.settings;

import com.nautilus.omni.appmodules.settings.mainsection.interactor.SettingsInteractorContract;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SettingsModule_ProvideSettingsInteractorFactory implements Factory<SettingsInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsModule_ProvideSettingsInteractorFactory.class.desiredAssertionStatus());
    private final SettingsModule module;
    private final Provider<ProductDaoHelper> productDaoHelperProvider;
    private final Provider<UserDaoHelper> userDaoHelperProvider;

    public SettingsModule_ProvideSettingsInteractorFactory(SettingsModule module2, Provider<UserDaoHelper> userDaoHelperProvider2, Provider<ProductDaoHelper> productDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || userDaoHelperProvider2 != null) {
                this.userDaoHelperProvider = userDaoHelperProvider2;
                if ($assertionsDisabled || productDaoHelperProvider2 != null) {
                    this.productDaoHelperProvider = productDaoHelperProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public SettingsInteractorContract get() {
        return (SettingsInteractorContract) Preconditions.checkNotNull(this.module.provideSettingsInteractor(this.userDaoHelperProvider.get(), this.productDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SettingsInteractorContract> create(SettingsModule module2, Provider<UserDaoHelper> userDaoHelperProvider2, Provider<ProductDaoHelper> productDaoHelperProvider2) {
        return new SettingsModule_ProvideSettingsInteractorFactory(module2, userDaoHelperProvider2, productDaoHelperProvider2);
    }

    public static SettingsInteractorContract proxyProvideSettingsInteractor(SettingsModule instance, UserDaoHelper userDaoHelper, ProductDaoHelper productDaoHelper) {
        return instance.provideSettingsInteractor(userDaoHelper, productDaoHelper);
    }
}
