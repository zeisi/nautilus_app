package com.nautilus.omni.appmodules.home.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class AchievementsPresenter_Factory implements Factory<AchievementsPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AchievementsPresenter_Factory.class.desiredAssertionStatus());
    private final MembersInjector<AchievementsPresenter> achievementsPresenterMembersInjector;
    private final Provider<Context> contextProvider;
    private final Provider<AchievementsContract> iAchievementsInteractorProvider;

    public AchievementsPresenter_Factory(MembersInjector<AchievementsPresenter> achievementsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<AchievementsContract> iAchievementsInteractorProvider2) {
        if ($assertionsDisabled || achievementsPresenterMembersInjector2 != null) {
            this.achievementsPresenterMembersInjector = achievementsPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || iAchievementsInteractorProvider2 != null) {
                    this.iAchievementsInteractorProvider = iAchievementsInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AchievementsPresenter get() {
        return (AchievementsPresenter) MembersInjectors.injectMembers(this.achievementsPresenterMembersInjector, new AchievementsPresenter(this.contextProvider.get(), this.iAchievementsInteractorProvider.get()));
    }

    public static Factory<AchievementsPresenter> create(MembersInjector<AchievementsPresenter> achievementsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<AchievementsContract> iAchievementsInteractorProvider2) {
        return new AchievementsPresenter_Factory(achievementsPresenterMembersInjector2, contextProvider2, iAchievementsInteractorProvider2);
    }
}
