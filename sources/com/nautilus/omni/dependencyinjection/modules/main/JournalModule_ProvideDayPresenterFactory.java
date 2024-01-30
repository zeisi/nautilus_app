package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayInteractor;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class JournalModule_ProvideDayPresenterFactory implements Factory<DayPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalModule_ProvideDayPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DayInteractor> dayInteractorProvider;
    private final JournalModule module;

    public JournalModule_ProvideDayPresenterFactory(JournalModule module2, Provider<Context> contextProvider2, Provider<DayInteractor> dayInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || dayInteractorProvider2 != null) {
                    this.dayInteractorProvider = dayInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DayPresenter get() {
        return (DayPresenter) Preconditions.checkNotNull(this.module.provideDayPresenter(this.contextProvider.get(), this.dayInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DayPresenter> create(JournalModule module2, Provider<Context> contextProvider2, Provider<DayInteractor> dayInteractorProvider2) {
        return new JournalModule_ProvideDayPresenterFactory(module2, contextProvider2, dayInteractorProvider2);
    }

    public static DayPresenter proxyProvideDayPresenter(JournalModule instance, Context context, DayInteractor dayInteractor) {
        return instance.provideDayPresenter(context, dayInteractor);
    }
}
