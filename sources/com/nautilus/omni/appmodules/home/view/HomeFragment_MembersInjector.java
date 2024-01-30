package com.nautilus.omni.appmodules.home.view;

import com.nautilus.omni.appmodules.home.presenter.HomePresenterContract;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class HomeFragment_MembersInjector implements MembersInjector<HomeFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<HomePresenterContract> mIHomePresenterProvider;

    public HomeFragment_MembersInjector(Provider<HomePresenterContract> mIHomePresenterProvider2) {
        if ($assertionsDisabled || mIHomePresenterProvider2 != null) {
            this.mIHomePresenterProvider = mIHomePresenterProvider2;
            return;
        }
        throw new AssertionError();
    }

    public static MembersInjector<HomeFragment> create(Provider<HomePresenterContract> mIHomePresenterProvider2) {
        return new HomeFragment_MembersInjector(mIHomePresenterProvider2);
    }

    public void injectMembers(HomeFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mIHomePresenter = this.mIHomePresenterProvider.get();
    }

    public static void injectMIHomePresenter(HomeFragment instance, Provider<HomePresenterContract> mIHomePresenterProvider2) {
        instance.mIHomePresenter = mIHomePresenterProvider2.get();
    }
}
