package com.nautilus.omni.appmodules.journal.view.year;

import com.nautilus.omni.appmodules.journal.presenter.year.YearPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class JournalYearFragment_MembersInjector implements MembersInjector<JournalYearFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalYearFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<YearPresenter> mYearPresenterProvider;

    public JournalYearFragment_MembersInjector(Provider<YearPresenter> mYearPresenterProvider2) {
        if ($assertionsDisabled || mYearPresenterProvider2 != null) {
            this.mYearPresenterProvider = mYearPresenterProvider2;
            return;
        }
        throw new AssertionError();
    }

    public static MembersInjector<JournalYearFragment> create(Provider<YearPresenter> mYearPresenterProvider2) {
        return new JournalYearFragment_MembersInjector(mYearPresenterProvider2);
    }

    public void injectMembers(JournalYearFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mYearPresenter = this.mYearPresenterProvider.get();
    }

    public static void injectMYearPresenter(JournalYearFragment instance, Provider<YearPresenter> mYearPresenterProvider2) {
        instance.mYearPresenter = mYearPresenterProvider2.get();
    }
}
