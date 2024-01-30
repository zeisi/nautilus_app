package com.nautilus.omni.appmodules.connectionwizard.domain.receivers;

import com.nautilus.omni.model.dto.OmniData;

public interface IWizardReceiversResponse {
    void onAddOmniDeviceResponse(OmniData omniData);

    void onShowErrorConnectingDialogReceiverResponse();

    void onSuccessfulConnectionReceiverResponse();

    void onUnexpectedDisconnProcessReceiverResponse();

    void onUpdateReceiverResponse(OmniData omniData);
}
