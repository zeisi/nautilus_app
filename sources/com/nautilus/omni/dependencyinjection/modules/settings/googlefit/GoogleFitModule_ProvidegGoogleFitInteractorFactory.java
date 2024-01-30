package com.nautilus.omni.dependencyinjection.modules.settings.googlefit;

import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractorContract;
import com.nautilus.omni.dataaccess.GoogleFitDaoHelper;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoogleFitModule_ProvidegGoogleFitInteractorFactory implements Factory<GoogleFitInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoogleFitModule_ProvidegGoogleFitInteractorFactory.class.desiredAssertionStatus());
    private final Provider<GoogleFitDaoHelper> googleFitDaoHelperProvider;
    private final GoogleFitModule module;
    private final Provider<OmniTrainerDaoHelper> omniTrainerDaoHelperProvider;

    public GoogleFitModule_ProvidegGoogleFitInteractorFactory(GoogleFitModule module2, Provider<GoogleFitDaoHelper> googleFitDaoHelperProvider2, Provider<OmniTrainerDaoHelper> omniTrainerDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || googleFitDaoHelperProvider2 != null) {
                this.googleFitDaoHelperProvider = googleFitDaoHelperProvider2;
                if ($assertionsDisabled || omniTrainerDaoHelperProvider2 != null) {
                    this.omniTrainerDaoHelperProvider = omniTrainerDaoHelperProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public GoogleFitInteractorContract get() {
        return (GoogleFitInteractorContract) Preconditions.checkNotNull(this.module.providegGoogleFitInteractor(this.googleFitDaoHelperProvider.get(), this.omniTrainerDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoogleFitInteractorContract> create(GoogleFitModule module2, Provider<GoogleFitDaoHelper> googleFitDaoHelperProvider2, Provider<OmniTrainerDaoHelper> omniTrainerDaoHelperProvider2) {
        return new GoogleFitModule_ProvidegGoogleFitInteractorFactory(module2, googleFitDaoHelperProvider2, omniTrainerDaoHelperProvider2);
    }

    public static GoogleFitInteractorContract proxyProvidegGoogleFitInteractor(GoogleFitModule instance, GoogleFitDaoHelper googleFitDaoHelper, OmniTrainerDaoHelper omniTrainerDaoHelper) {
        return instance.providegGoogleFitInteractor(googleFitDaoHelper, omniTrainerDaoHelper);
    }
}
