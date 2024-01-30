package com.nautilus.omni.appmodules.home.view;

import com.nautilus.omni.appmodules.home.presenter.GaugePresenterContract;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GaugeFragment_MembersInjector implements MembersInjector<GaugeFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GaugeFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<GaugePresenterContract> mIGaugePresenterProvider;

    public GaugeFragment_MembersInjector(Provider<GaugePresenterContract> mIGaugePresenterProvider2) {
        if ($assertionsDisabled || mIGaugePresenterProvider2 != null) {
            this.mIGaugePresenterProvider = mIGaugePresenterProvider2;
            return;
        }
        throw new AssertionError();
    }

    public static MembersInjector<GaugeFragment> create(Provider<GaugePresenterContract> mIGaugePresenterProvider2) {
        return new GaugeFragment_MembersInjector(mIGaugePresenterProvider2);
    }

    public void injectMembers(GaugeFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mIGaugePresenter = this.mIGaugePresenterProvider.get();
    }

    public static void injectMIGaugePresenter(GaugeFragment instance, Provider<GaugePresenterContract> mIGaugePresenterProvider2) {
        instance.mIGaugePresenter = mIGaugePresenterProvider2.get();
    }
}
