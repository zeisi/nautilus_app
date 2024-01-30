package com.nautilus.omni.appmodules.connectionwizard.domain.receivers;

import com.nautilus.omni.model.dto.OmniData;

public interface IOmniDevicesListReceivers {
    void onAddOmniTrainerToListReceiverResponse(OmniData omniData);

    void onConnectionErrorReceiverResponse();

    void onOmniTrainerUpdatesReceiverResponse(OmniData omniData);

    void onSuccessfulConnectionReceiverResponse();

    void onUnexpectedDisconnectionProcessReceiverResponse();
}
