package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.year.YearInteractor;
import com.nautilus.omni.appmodules.journal.presenter.year.YearPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class JournalModule_ProvideYearPresenterFactory implements Factory<YearPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalModule_ProvideYearPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final JournalModule module;
    private final Provider<YearInteractor> yearInteractorProvider;

    public JournalModule_ProvideYearPresenterFactory(JournalModule module2, Provider<Context> contextProvider2, Provider<YearInteractor> yearInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || yearInteractorProvider2 != null) {
                    this.yearInteractorProvider = yearInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public YearPresenter get() {
        return (YearPresenter) Preconditions.checkNotNull(this.module.provideYearPresenter(this.contextProvider.get(), this.yearInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<YearPresenter> create(JournalModule module2, Provider<Context> contextProvider2, Provider<YearInteractor> yearInteractorProvider2) {
        return new JournalModule_ProvideYearPresenterFactory(module2, contextProvider2, yearInteractorProvider2);
    }

    public static YearPresenter proxyProvideYearPresenter(JournalModule instance, Context context, YearInteractor yearInteractor) {
        return instance.provideYearPresenter(context, yearInteractor);
    }
}
