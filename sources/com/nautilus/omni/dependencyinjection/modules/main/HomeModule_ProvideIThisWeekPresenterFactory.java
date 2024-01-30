package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.home.domain.helpers.IThisWeekHelper;
import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractorContract;
import com.nautilus.omni.appmodules.home.presenter.ThisWeekPresenterContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIThisWeekPresenterFactory implements Factory<ThisWeekPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIThisWeekPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final HomeModule module;
    private final Provider<IThisWeekHelper> thisWeekHelperProvider;
    private final Provider<ThisWeekSectionInteractorContract> thisWeekSectionInteractorProvider;

    public HomeModule_ProvideIThisWeekPresenterFactory(HomeModule module2, Provider<Context> contextProvider2, Provider<IThisWeekHelper> thisWeekHelperProvider2, Provider<ThisWeekSectionInteractorContract> thisWeekSectionInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || thisWeekHelperProvider2 != null) {
                    this.thisWeekHelperProvider = thisWeekHelperProvider2;
                    if ($assertionsDisabled || thisWeekSectionInteractorProvider2 != null) {
                        this.thisWeekSectionInteractorProvider = thisWeekSectionInteractorProvider2;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public ThisWeekPresenterContract get() {
        return (ThisWeekPresenterContract) Preconditions.checkNotNull(this.module.provideIThisWeekPresenter(this.contextProvider.get(), this.thisWeekHelperProvider.get(), this.thisWeekSectionInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ThisWeekPresenterContract> create(HomeModule module2, Provider<Context> contextProvider2, Provider<IThisWeekHelper> thisWeekHelperProvider2, Provider<ThisWeekSectionInteractorContract> thisWeekSectionInteractorProvider2) {
        return new HomeModule_ProvideIThisWeekPresenterFactory(module2, contextProvider2, thisWeekHelperProvider2, thisWeekSectionInteractorProvider2);
    }

    public static ThisWeekPresenterContract proxyProvideIThisWeekPresenter(HomeModule instance, Context context, IThisWeekHelper thisWeekHelper, ThisWeekSectionInteractorContract thisWeekSectionInteractor) {
        return instance.provideIThisWeekPresenter(context, thisWeekHelper, thisWeekSectionInteractor);
    }
}
