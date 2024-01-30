package com.nautilus.omni.dependencyinjection.modules.awardstypes;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractor;
import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractorContract;
import com.nautilus.omni.appmodules.awardtypes.presenter.AwardTypesPresenter;
import com.nautilus.omni.appmodules.awardtypes.presenter.AwardTypesPresenterContract;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivityContract;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.permissions.SupportPermissionActionImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class AwardTypesModule {
    final AwardTypesActivity iAwardTypes;

    public AwardTypesModule(AwardTypesActivity iAwardTypes2) {
        this.iAwardTypes = iAwardTypes2;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardTypesActivity provideAwardsView() {
        return this.iAwardTypes;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardTypesActivityContract provideIAwardsView() {
        return this.iAwardTypes;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardsTypesInteractorContract provideAwardsTypesInteractor(AwardsDaoHelper awardsDaoHelper) {
        return new AwardsTypesInteractor(awardsDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardTypesPresenterContract provideAwardTypesPresenter(Context context, AwardsTypesInteractorContract awardsInteractorContract, AwardTypesActivityContract awardTypesActivityContract) {
        return new AwardTypesPresenter(context, awardsInteractorContract, awardTypesActivityContract);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionAction providePermissionAction(AwardTypesActivity awardTypesActivity) {
        return new SupportPermissionActionImpl(awardTypesActivity);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionPresenter providePermissionPresenter(PermissionAction permissionAction, AwardTypesActivity awardTypesActivity) {
        return new PermissionPresenter(permissionAction, awardTypesActivity);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardValueBuilder provideAwardValueBuilder(Context context, WeekDaoHelper weekDaoHelper) {
        return new AwardValueBuilder(context, weekDaoHelper);
    }
}
