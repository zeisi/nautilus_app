package com.nautilus.omni.appmodules.home.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.home.domain.helpers.IThisWeekHelper;
import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractorContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class ThisWeekPresenter_Factory implements Factory<ThisWeekPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ThisWeekPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<IThisWeekHelper> thisWeekHelperProvider;
    private final MembersInjector<ThisWeekPresenter> thisWeekPresenterMembersInjector;
    private final Provider<ThisWeekSectionInteractorContract> thisWeekSectionInteractorProvider;

    public ThisWeekPresenter_Factory(MembersInjector<ThisWeekPresenter> thisWeekPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<IThisWeekHelper> thisWeekHelperProvider2, Provider<ThisWeekSectionInteractorContract> thisWeekSectionInteractorProvider2) {
        if ($assertionsDisabled || thisWeekPresenterMembersInjector2 != null) {
            this.thisWeekPresenterMembersInjector = thisWeekPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || thisWeekHelperProvider2 != null) {
                    this.thisWeekHelperProvider = thisWeekHelperProvider2;
                    if ($assertionsDisabled || thisWeekSectionInteractorProvider2 != null) {
                        this.thisWeekSectionInteractorProvider = thisWeekSectionInteractorProvider2;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public ThisWeekPresenter get() {
        return (ThisWeekPresenter) MembersInjectors.injectMembers(this.thisWeekPresenterMembersInjector, new ThisWeekPresenter(this.contextProvider.get(), this.thisWeekHelperProvider.get(), this.thisWeekSectionInteractorProvider.get()));
    }

    public static Factory<ThisWeekPresenter> create(MembersInjector<ThisWeekPresenter> thisWeekPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<IThisWeekHelper> thisWeekHelperProvider2, Provider<ThisWeekSectionInteractorContract> thisWeekSectionInteractorProvider2) {
        return new ThisWeekPresenter_Factory(thisWeekPresenterMembersInjector2, contextProvider2, thisWeekHelperProvider2, thisWeekSectionInteractorProvider2);
    }
}
