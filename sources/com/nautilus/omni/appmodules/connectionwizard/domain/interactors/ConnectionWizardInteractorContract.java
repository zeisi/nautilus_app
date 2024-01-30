package com.nautilus.omni.appmodules.connectionwizard.domain.interactors;

import com.nautilus.omni.model.dto.OmniData;

public interface ConnectionWizardInteractorContract {

    public interface OnConnectionWizardInteractorResponse {
        void onCreateSuccess(OmniData omniData);
    }

    void createOmniTrainerOnListAppearNew(OmniData omniData);

    void deleteOmniData();

    OmniData getFirstIOmniData();

    void saveOmniTrainerInDataBase(OmniData omniData);

    void setResponseInterface(OnConnectionWizardInteractorResponse onConnectionWizardInteractorResponse);

    void updateOmniData(OmniData omniData);
}
