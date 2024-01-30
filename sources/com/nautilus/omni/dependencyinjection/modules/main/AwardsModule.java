package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractor;
import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractorContract;
import com.nautilus.omni.appmodules.awards.presenter.AwardsPresenter;
import com.nautilus.omni.appmodules.awards.presenter.AwardsPresenterContract;
import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class AwardsModule {
    AwardsFragment mAwardsView;

    public AwardsModule(AwardsFragment awardsView) {
        this.mAwardsView = awardsView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardsFragment provideAwardsView() {
        return this.mAwardsView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardsInteractorContract provideIAwardsInteractor(AwardsDaoHelper awardsDaoHelper) {
        return new AwardsInteractor(awardsDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardsPresenterContract provideIAwardsPresenter(Context context, AwardsInteractorContract awardsInteractorContract) {
        return new AwardsPresenter(context, awardsInteractorContract);
    }
}
