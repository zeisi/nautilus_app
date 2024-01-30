package com.nautilus.omni.appmodules.settings.googlefit.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractorContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class GoogleFitPresenter_Factory implements Factory<GoogleFitPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoogleFitPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<GoogleFitInteractorContract> googleFitInteractorProvider;
    private final MembersInjector<GoogleFitPresenter> googleFitPresenterMembersInjector;

    public GoogleFitPresenter_Factory(MembersInjector<GoogleFitPresenter> googleFitPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<GoogleFitInteractorContract> googleFitInteractorProvider2) {
        if ($assertionsDisabled || googleFitPresenterMembersInjector2 != null) {
            this.googleFitPresenterMembersInjector = googleFitPresenterMembersInjector2;
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

    public GoogleFitPresenter get() {
        return (GoogleFitPresenter) MembersInjectors.injectMembers(this.googleFitPresenterMembersInjector, new GoogleFitPresenter(this.contextProvider.get(), this.googleFitInteractorProvider.get()));
    }

    public static Factory<GoogleFitPresenter> create(MembersInjector<GoogleFitPresenter> googleFitPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<GoogleFitInteractorContract> googleFitInteractorProvider2) {
        return new GoogleFitPresenter_Factory(googleFitPresenterMembersInjector2, contextProvider2, googleFitInteractorProvider2);
    }
}
