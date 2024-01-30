package com.nautilus.omni.appmodules.connectionwizard.domain.interactors;

import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.model.dto.OmniData;

public class ConnectionWizardInteractor implements ConnectionWizardInteractorContract, OmniTrainerDaoHelper.OnResponseOmniTrainerDaoHelper {
    OmniTrainerDaoHelper omniTrainerDaoHelper;
    ConnectionWizardInteractorContract.OnConnectionWizardInteractorResponse response;

    public void setResponseInterface(ConnectionWizardInteractorContract.OnConnectionWizardInteractorResponse response2) {
        this.response = response2;
    }

    public ConnectionWizardInteractor(OmniTrainerDaoHelper omniTrainerDaoHelper2) {
        this.omniTrainerDaoHelper = omniTrainerDaoHelper2;
    }

    public void saveOmniTrainerInDataBase(OmniData omniData) {
        this.omniTrainerDaoHelper.saveOmniTrainerInDataBase(omniData);
    }

    public void createOmniTrainerOnListAppearNew(OmniData omniData) {
        this.omniTrainerDaoHelper.createOmniData(omniData, this);
    }

    public void updateOmniData(OmniData omniData) {
        this.omniTrainerDaoHelper.updateOmniData(omniData, this);
    }

    public void deleteOmniData() {
        this.omniTrainerDaoHelper.deleteOmniData(this);
    }

    public OmniData getFirstIOmniData() {
        return this.omniTrainerDaoHelper.getFirstOmniData();
    }

    public void onCreateSuccess(OmniData omniData) {
        this.response.onCreateSuccess(omniData);
    }

    public void errorCreateOmniData() {
    }
}
