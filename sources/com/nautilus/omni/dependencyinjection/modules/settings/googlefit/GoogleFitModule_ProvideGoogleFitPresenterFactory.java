package com.nautilus.omni.dependencyinjection.modules.settings.googlefit;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractorContract;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenterContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoogleFitModule_ProvideGoogleFitPresenterFactory implements Factory<GoogleFitPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoogleFitModule_ProvideGoogleFitPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<GoogleFitInteractorContract> googleFitInteractorProvider;
    private final GoogleFitModule module;

    public GoogleFitModule_ProvideGoogleFitPresenterFactory(GoogleFitModule module2, Provider<Context> contextProvider2, Provider<GoogleFitInteractorContract> googleFitInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || googleFitInteractorProvider2 != null) {
                    this.googleFitInteractorProvider = googleFitInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public GoogleFitPresenterContract get() {
        return (GoogleFitPresenterContract) Preconditions.checkNotNull(this.module.provideGoogleFitPresenter(this.contextProvider.get(), this.googleFitInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoogleFitPresenterContract> create(GoogleFitModule module2, Provider<Context> contextProvider2, Provider<GoogleFitInteractorContract> googleFitInteractorProvider2) {
        return new GoogleFitModule_ProvideGoogleFitPresenterFactory(module2, contextProvider2, googleFitInteractorProvider2);
    }

    public static GoogleFitPresenterContract proxyProvideGoogleFitPresenter(GoogleFitModule instance, Context context, GoogleFitInteractorContract googleFitInteractor) {
        return instance.provideGoogleFitPresenter(context, googleFitInteractor);
    }
}
