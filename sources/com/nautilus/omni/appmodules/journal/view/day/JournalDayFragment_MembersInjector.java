package com.nautilus.omni.appmodules.journal.view.day;

import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class JournalDayFragment_MembersInjector implements MembersInjector<JournalDayFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalDayFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<DayPresenter> mDayPresenterProvider;

    public JournalDayFragment_MembersInjector(Provider<DayPresenter> mDayPresenterProvider2) {
        if ($assertionsDisabled || mDayPresenterProvider2 != null) {
            this.mDayPresenterProvider = mDayPresenterProvider2;
            return;
        }
        throw new AssertionError();
    }

    public static MembersInjector<JournalDayFragment> create(Provider<DayPresenter> mDayPresenterProvider2) {
        return new JournalDayFragment_MembersInjector(mDayPresenterProvider2);
    }

    public void injectMembers(JournalDayFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mDayPresenter = this.mDayPresenterProvider.get();
    }

    public static void injectMDayPresenter(JournalDayFragment instance, Provider<DayPresenter> mDayPresenterProvider2) {
        instance.mDayPresenter = mDayPresenterProvider2.get();
    }
}
