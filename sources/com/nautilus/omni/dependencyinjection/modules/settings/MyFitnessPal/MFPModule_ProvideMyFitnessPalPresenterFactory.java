package com.nautilus.omni.dependencyinjection.modules.settings.MyFitnessPal;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.myfitnesspal.presenter.MyFitnessPalPresenterContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class MFPModule_ProvideMyFitnessPalPresenterFactory implements Factory<MyFitnessPalPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MFPModule_ProvideMyFitnessPalPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MFPModule module;

    public MFPModule_ProvideMyFitnessPalPresenterFactory(MFPModule module2, Provider<Context> contextProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public MyFitnessPalPresenterContract get() {
        return (MyFitnessPalPresenterContract) Preconditions.checkNotNull(this.module.provideMyFitnessPalPresenter(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MyFitnessPalPresenterContract> create(MFPModule module2, Provider<Context> contextProvider2) {
        return new MFPModule_ProvideMyFitnessPalPresenterFactory(module2, contextProvider2);
    }

    public static MyFitnessPalPresenterContract proxyProvideMyFitnessPalPresenter(MFPModule instance, Context context) {
        return instance.provideMyFitnessPalPresenter(context);
    }
}
