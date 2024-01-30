package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

import dagger.internal.Factory;
import javax.inject.Provider;

public final class DayListBuilderInteractor_Factory implements Factory<DayListBuilderInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DayListBuilderInteractor_Factory.class.desiredAssertionStatus());
    private final Provider<DayInteractor> dayInteractorProvider;

    public DayListBuilderInteractor_Factory(Provider<DayInteractor> dayInteractorProvider2) {
        if ($assertionsDisabled || dayInteractorProvider2 != null) {
            this.dayInteractorProvider = dayInteractorProvider2;
            return;
        }
        throw new AssertionError();
    }

    public DayListBuilderInteractor get() {
        return new DayListBuilderInteractor(this.dayInteractorProvider.get());
    }

    public static Factory<DayListBuilderInteractor> create(Provider<DayInteractor> dayInteractorProvider2) {
        return new DayListBuilderInteractor_Factory(dayInteractorProvider2);
    }
}
