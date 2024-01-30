package com.nautilus.omni.dependencyinjection.modules.settings.underarmour;

import android.content.Context;
import com.nautilus.underarmourconnection.services.authentication.AuthenticationService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UnderArmourModule_ProvideUnderArmourAuthenticationServiceFactory implements Factory<AuthenticationService> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UnderArmourModule_ProvideUnderArmourAuthenticationServiceFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final UnderArmourModule module;

    public UnderArmourModule_ProvideUnderArmourAuthenticationServiceFactory(UnderArmourModule module2, Provider<Context> contextProvider2) {
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

    public AuthenticationService get() {
        return (AuthenticationService) Preconditions.checkNotNull(this.module.provideUnderArmourAuthenticationService(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AuthenticationService> create(UnderArmourModule module2, Provider<Context> contextProvider2) {
        return new UnderArmourModule_ProvideUnderArmourAuthenticationServiceFactory(module2, contextProvider2);
    }
}
