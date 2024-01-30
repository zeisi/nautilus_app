package com.nautilus.omni.appmodules.settings.myfitnesspal.presenter;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class MyFitnessPalPresenter_Factory implements Factory<MyFitnessPalPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MyFitnessPalPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MembersInjector<MyFitnessPalPresenter> myFitnessPalPresenterMembersInjector;

    public MyFitnessPalPresenter_Factory(MembersInjector<MyFitnessPalPresenter> myFitnessPalPresenterMembersInjector2, Provider<Context> contextProvider2) {
        if ($assertionsDisabled || myFitnessPalPresenterMembersInjector2 != null) {
            this.myFitnessPalPresenterMembersInjector = myFitnessPalPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public MyFitnessPalPresenter get() {
        return (MyFitnessPalPresenter) MembersInjectors.injectMembers(this.myFitnessPalPresenterMembersInjector, new MyFitnessPalPresenter(this.contextProvider.get()));
    }

    public static Factory<MyFitnessPalPresenter> create(MembersInjector<MyFitnessPalPresenter> myFitnessPalPresenterMembersInjector2, Provider<Context> contextProvider2) {
        return new MyFitnessPalPresenter_Factory(myFitnessPalPresenterMembersInjector2, contextProvider2);
    }
}
