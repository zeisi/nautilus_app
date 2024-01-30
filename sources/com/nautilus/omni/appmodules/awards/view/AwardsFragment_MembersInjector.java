package com.nautilus.omni.appmodules.awards.view;

import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awards.presenter.AwardsPresenterContract;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AwardsFragment_MembersInjector implements MembersInjector<AwardsFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardsFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<AwardValueBuilder> mAwardValueBuilderProvider;
    private final Provider<AwardsPresenterContract> mAwardsPresenterProvider;

    public AwardsFragment_MembersInjector(Provider<AwardValueBuilder> mAwardValueBuilderProvider2, Provider<AwardsPresenterContract> mAwardsPresenterProvider2) {
        if ($assertionsDisabled || mAwardValueBuilderProvider2 != null) {
            this.mAwardValueBuilderProvider = mAwardValueBuilderProvider2;
            if ($assertionsDisabled || mAwardsPresenterProvider2 != null) {
                this.mAwardsPresenterProvider = mAwardsPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<AwardsFragment> create(Provider<AwardValueBuilder> mAwardValueBuilderProvider2, Provider<AwardsPresenterContract> mAwardsPresenterProvider2) {
        return new AwardsFragment_MembersInjector(mAwardValueBuilderProvider2, mAwardsPresenterProvider2);
    }

    public void injectMembers(AwardsFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mAwardValueBuilder = this.mAwardValueBuilderProvider.get();
        instance.mAwardsPresenter = this.mAwardsPresenterProvider.get();
    }

    public static void injectMAwardValueBuilder(AwardsFragment instance, Provider<AwardValueBuilder> mAwardValueBuilderProvider2) {
        instance.mAwardValueBuilder = mAwardValueBuilderProvider2.get();
    }

    public static void injectMAwardsPresenter(AwardsFragment instance, Provider<AwardsPresenterContract> mAwardsPresenterProvider2) {
        instance.mAwardsPresenter = mAwardsPresenterProvider2.get();
    }
}
