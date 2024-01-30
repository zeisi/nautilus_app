package com.nautilus.omni.appmodules.journal.view.week;

import com.nautilus.omni.appmodules.journal.presenter.week.WeekPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class JournalWeekFragment_MembersInjector implements MembersInjector<JournalWeekFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalWeekFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<WeekPresenter> mWeekPresenterProvider;

    public JournalWeekFragment_MembersInjector(Provider<WeekPresenter> mWeekPresenterProvider2) {
        if ($assertionsDisabled || mWeekPresenterProvider2 != null) {
            this.mWeekPresenterProvider = mWeekPresenterProvider2;
            return;
        }
        throw new AssertionError();
    }

    public static MembersInjector<JournalWeekFragment> create(Provider<WeekPresenter> mWeekPresenterProvider2) {
        return new JournalWeekFragment_MembersInjector(mWeekPresenterProvider2);
    }

    public void injectMembers(JournalWeekFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mWeekPresenter = this.mWeekPresenterProvider.get();
    }

    public static void injectMWeekPresenter(JournalWeekFragment instance, Provider<WeekPresenter> mWeekPresenterProvider2) {
        instance.mWeekPresenter = mWeekPresenterProvider2.get();
    }
}
