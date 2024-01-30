package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class WizardDataModule_ProvideReceiversFactory implements Factory<WizardReceivers> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WizardDataModule_ProvideReceiversFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final WizardDataModule module;

    public WizardDataModule_ProvideReceiversFactory(WizardDataModule module2, Provider<Context> contextProvider2) {
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

    public WizardReceivers get() {
        return (WizardReceivers) Preconditions.checkNotNull(this.module.provideReceivers(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WizardReceivers> create(WizardDataModule module2, Provider<Context> contextProvider2) {
        return new WizardDataModule_ProvideReceiversFactory(module2, contextProvider2);
    }

    public static WizardReceivers proxyProvideReceivers(WizardDataModule instance, Context context) {
        return instance.provideReceivers(context);
    }
}
