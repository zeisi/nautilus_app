package com.nautilus.omni.app;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class BasePresenter_Factory implements Factory<BasePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!BasePresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;

    public BasePresenter_Factory(Provider<Context> contextProvider2) {
        if ($assertionsDisabled || contextProvider2 != null) {
            this.contextProvider = contextProvider2;
            return;
        }
        throw new AssertionError();
    }

    public BasePresenter get() {
        return new BasePresenter(this.contextProvider.get());
    }

    public static Factory<BasePresenter> create(Provider<Context> contextProvider2) {
        return new BasePresenter_Factory(contextProvider2);
    }
}
