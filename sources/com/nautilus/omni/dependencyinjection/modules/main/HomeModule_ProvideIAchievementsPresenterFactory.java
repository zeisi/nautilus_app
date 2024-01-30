package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsContract;
import com.nautilus.omni.appmodules.home.presenter.AchievementsPresenterContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIAchievementsPresenterFactory implements Factory<AchievementsPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIAchievementsPresenterFactory.class.desiredAssertionStatus());
    private final Provider<AchievementsContract> achievementsInteractorProvider;
    private final Provider<Context> contextProvider;
    private final HomeModule module;

    public HomeModule_ProvideIAchievementsPresenterFactory(HomeModule module2, Provider<Context> contextProvider2, Provider<AchievementsContract> achievementsInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || achievementsInteractorProvider2 != null) {
                    this.achievementsInteractorProvider = achievementsInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AchievementsPresenterContract get() {
        return (AchievementsPresenterContract) Preconditions.checkNotNull(this.module.provideIAchievementsPresenter(this.contextProvider.get(), this.achievementsInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AchievementsPresenterContract> create(HomeModule module2, Provider<Context> contextProvider2, Provider<AchievementsContract> achievementsInteractorProvider2) {
        return new HomeModule_ProvideIAchievementsPresenterFactory(module2, contextProvider2, achievementsInteractorProvider2);
    }

    public static AchievementsPresenterContract proxyProvideIAchievementsPresenter(HomeModule instance, Context context, AchievementsContract achievementsInteractor) {
        return instance.provideIAchievementsPresenter(context, achievementsInteractor);
    }
}
