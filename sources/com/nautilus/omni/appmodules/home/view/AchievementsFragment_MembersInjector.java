package com.nautilus.omni.appmodules.home.view;

import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.home.presenter.AchievementsPresenterContract;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AchievementsFragment_MembersInjector implements MembersInjector<AchievementsFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AchievementsFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<AchievementsPresenterContract> iAchievementsPresenterProvider;
    private final Provider<AwardValueBuilder> mAwardValueBuilderProvider;

    public AchievementsFragment_MembersInjector(Provider<AchievementsPresenterContract> iAchievementsPresenterProvider2, Provider<AwardValueBuilder> mAwardValueBuilderProvider2) {
        if ($assertionsDisabled || iAchievementsPresenterProvider2 != null) {
            this.iAchievementsPresenterProvider = iAchievementsPresenterProvider2;
            if ($assertionsDisabled || mAwardValueBuilderProvider2 != null) {
                this.mAwardValueBuilderProvider = mAwardValueBuilderProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<AchievementsFragment> create(Provider<AchievementsPresenterContract> iAchievementsPresenterProvider2, Provider<AwardValueBuilder> mAwardValueBuilderProvider2) {
        return new AchievementsFragment_MembersInjector(iAchievementsPresenterProvider2, mAwardValueBuilderProvider2);
    }

    public void injectMembers(AchievementsFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.iAchievementsPresenter = this.iAchievementsPresenterProvider.get();
        instance.mAwardValueBuilder = this.mAwardValueBuilderProvider.get();
    }

    public static void injectIAchievementsPresenter(AchievementsFragment instance, Provider<AchievementsPresenterContract> iAchievementsPresenterProvider2) {
        instance.iAchievementsPresenter = iAchievementsPresenterProvider2.get();
    }

    public static void injectMAwardValueBuilder(AchievementsFragment instance, Provider<AwardValueBuilder> mAwardValueBuilderProvider2) {
        instance.mAwardValueBuilder = mAwardValueBuilderProvider2.get();
    }
}
