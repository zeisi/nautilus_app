package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekInteractor;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class JournalModule_ProvideWeekPresenterFactory implements Factory<WeekPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalModule_ProvideWeekPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final JournalModule module;
    private final Provider<WeekInteractor> weekInteractorProvider;

    public JournalModule_ProvideWeekPresenterFactory(JournalModule module2, Provider<Context> contextProvider2, Provider<WeekInteractor> weekInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || weekInteractorProvider2 != null) {
                    this.weekInteractorProvider = weekInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public WeekPresenter get() {
        return (WeekPresenter) Preconditions.checkNotNull(this.module.provideWeekPresenter(this.contextProvider.get(), this.weekInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WeekPresenter> create(JournalModule module2, Provider<Context> contextProvider2, Provider<WeekInteractor> weekInteractorProvider2) {
        return new JournalModule_ProvideWeekPresenterFactory(module2, contextProvider2, weekInteractorProvider2);
    }

    public static WeekPresenter proxyProvideWeekPresenter(JournalModule instance, Context context, WeekInteractor weekInteractor) {
        return instance.provideWeekPresenter(context, weekInteractor);
    }
}
